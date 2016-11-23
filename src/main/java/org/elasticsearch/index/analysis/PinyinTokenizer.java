package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.elasticsearch.analysis.PinyinConfig;
import org.nlpcn.commons.lang.pinyin.Pinyin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class PinyinTokenizer extends Tokenizer {


    private static final int DEFAULT_BUFFER_SIZE = 256;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private boolean done = false;
    private boolean processedCandidate = false;
    private boolean processedFirstLetter = false;
    private boolean processedFullPinyinLetter = false;
    private boolean processedOriginal = false;
    protected int position = 0;
    protected int lastPosition = 0;
    private OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private PinyinConfig config;
    ArrayList<TermItem> candidate;
    private HashSet<String> termsFilter;
    StringBuilder firstLetters;
    StringBuilder fullPinyinLetters;

    String source;

    public PinyinTokenizer(PinyinConfig config) {
        this(DEFAULT_BUFFER_SIZE);
        this.config = config;

        //validate config
        if (!(config.keepFirstLetter || config.keepFullPinyin || config.keepJoinedFullPinyin)) {
            throw new ConfigErrorException("pinyin config error, can't disable first_letter and full_pinyin at the same time.");
        }
        candidate = new ArrayList<>();
        termsFilter = new HashSet<>();
        firstLetters = new StringBuilder();
        fullPinyinLetters = new StringBuilder();
    }

    public PinyinTokenizer(int bufferSize) {
        super();
        termAtt.resizeBuffer(bufferSize);
    }

    void addCandidate(TermItem item) {

        String term = item.term;
        if (config.lowercase) {
            term = term.toLowerCase();
        }

        if (config.trimWhitespace) {
            term = term.trim();
        }
        item.term = term;

        if (config.removeDuplicateTerm) {
            if (termsFilter.contains(term)) {
                return;
            }
            termsFilter.add(term);
        }
        candidate.add(item);
    }

    void setTerm(String term, int startOffset, int endOffset) {
        if (config.lowercase) {
            term = term.toLowerCase();
        }

        if (config.trimWhitespace) {
            term = term.trim();
        }
        termAtt.setEmpty();
        termAtt.append(term);
        if(startOffset<0){startOffset=0;}
        if(endOffset<startOffset){endOffset=startOffset+term.length();}
        offsetAtt.setOffset(correctOffset(startOffset), correctOffset(endOffset));
    }

    @Override
    public final boolean incrementToken() throws IOException {

        clearAttributes();

        if (!done) {

            //combine text together to get right pinyin
            if (!processedCandidate) {
                processedCandidate = true;
                int upto = 0;
                char[] buffer = termAtt.buffer();
                while (true) {
                    final int length = input.read(buffer, upto, buffer.length - upto);
                    if (length == -1) break;
                    upto += length;
                    if (upto == buffer.length)
                        buffer = termAtt.resizeBuffer(1 + buffer.length);
                }
                termAtt.setLength(upto);
                source = termAtt.toString();

                List<String> pinyinList = Pinyin.pinyin(source);

                StringBuilder buff = new StringBuilder();
                int buffSize = 0;

                for (int i = 0; i < source.length(); i++) {
                    char c = source.charAt(i);
                    lastPosition=i;
                    //keep original alphabet
                    if (c < 128) {
                        if ((c > 96 && c < 123) || (c > 64 && c < 91) || (c > 47 && c < 58)) {
                            if (config.keepNoneChinese) {
                                if (config.keepNoneChinese) {
                                    if (config.keepNoneChineseTogether) {
                                        buff.append(c);
                                        buffSize++;
                                    } else {
                                        addCandidate(new TermItem(String.valueOf(c), i, i + 1));
                                    }
                                }
                            }
                            if (config.keepNoneChineseInFirstLetter) {
                                firstLetters.append(c);
                            }
                        }
                    } else {
                        //clean previous temp
                        if (buff.length() > 0) {
                            buffSize = parseBuff(buff, buffSize);
                        }

                        String pinyin = pinyinList.get(i);
                        if (pinyin != null && pinyin.length() > 0) {

                            firstLetters.append(pinyin.charAt(0));
                            if (config.keepSeparateFirstLetter & pinyin.length() > 1) {
                                addCandidate(new TermItem(String.valueOf(pinyin.charAt(0)), i, i + 1));
                            }
                            if (config.keepFullPinyin) {
                                addCandidate(new TermItem(pinyin, i, i + 1));
                            }
                            if(config.keepJoinedFullPinyin){
                                fullPinyinLetters.append(pinyin);
                            }
                        }
                    }
                }

                //clean previous temp
                if (buff.length() > 0) {
                    buffSize = parseBuff(buff, buffSize);
                }
            }

            if (config.keepOriginal && !processedOriginal) {
                processedOriginal = true;
                addCandidate(new TermItem(source, 0, source.length()));
            }

            if (config.keepJoinedFullPinyin && !processedFullPinyinLetter) {
                processedFullPinyinLetter = true;
                addCandidate(new TermItem(fullPinyinLetters.toString(), 0, fullPinyinLetters.length()));
                fullPinyinLetters.setLength(0);
            }


            if (config.keepFirstLetter && firstLetters.length() > 0 && !processedFirstLetter) {
                processedFirstLetter = true;
                String fl;
                if (firstLetters.length() > config.LimitFirstLetterLength && config.LimitFirstLetterLength > 0) {
                    fl = firstLetters.substring(0, config.LimitFirstLetterLength);
                } else {
                    fl = firstLetters.toString();
                }
                if (config.lowercase) {
                    fl = fl.toLowerCase();
                }
                if (!(config.keepSeparateFirstLetter && fl.length() <= 1)) {
                    addCandidate(new TermItem(fl, 0, fl.length()));
                }
            }


            if (position < candidate.size()) {
                TermItem item = candidate.get(position);
                position++;
                setTerm(item.term, item.startOffset, item.endOffset);
                return true;
            }


            done = true;
            return false;
        }
        return false;
    }

    private int parseBuff(StringBuilder buff, int buffSize) {
        if (config.keepNoneChinese) {
            if(config.noneChinesePinyinTokenize){
                List<String> result = PinyinAlphabetTokenizer.walk(buff.toString());
                for (int i = 0; i < result.size(); i++) {
                    addCandidate(new TermItem(result.get(i), lastPosition - buffSize, lastPosition));
                }
            }else{
                addCandidate(new TermItem(buff.toString(), lastPosition - buffSize, lastPosition));
            }
        }

        buff.setLength(0);
        buffSize = 0;
        return buffSize;
    }

    @Override
    public final void end() throws IOException {
        super.end();
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        position = 0;
        this.done = false;
        this.processedCandidate = false;
        this.processedFirstLetter = false;
        this.processedFullPinyinLetter = false;
        this.processedOriginal = false;
        firstLetters.setLength(0);
        fullPinyinLetters.setLength(0);
        termsFilter.clear();
        candidate.clear();
        source = null;
    }


}
