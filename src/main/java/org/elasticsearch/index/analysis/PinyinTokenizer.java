package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.elasticsearch.analysis.PinyinConfig;
import org.nlpcn.commons.lang.pinyin.Pinyin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Medcl'
 * Date: 12-5-21
 * Time: 下午5:53
 */
public class PinyinTokenizer extends Tokenizer {

    private static final int DEFAULT_BUFFER_SIZE = 256;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private boolean done = false;
    private boolean processedCandidate = false;
    private boolean processedFirstLetter = false;
    private boolean processedOriginal = false;
    protected int position = 0;
    protected int lastPosition = 0;
    private OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private PinyinConfig config;
    List<String> candidate;
    StringBuilder firstLetters;
    String source;

    public PinyinTokenizer(PinyinConfig config) {
        this(DEFAULT_BUFFER_SIZE);
        this.config = config;

        //validate config
        if (!(config.keepFirstLetter || config.keepFullPinyin)) {
            throw new ConfigErrorException("pinyin config error, can't disable first_letter and full_pinyin at the same time.");
        }
        candidate = new ArrayList<>();
        firstLetters = new StringBuilder();
    }

    public PinyinTokenizer(int bufferSize) {
        super();
        termAtt.resizeBuffer(bufferSize);
    }

    @Override
    public final boolean incrementToken() throws IOException {

        clearAttributes();

        if (!done) {

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
                lastPosition = upto;
                termAtt.setLength(upto);
                source = termAtt.toString();

                if (config.trimWhitespace) {
                    source = source.trim();
                }

                List<String> pinyinList = Pinyin.pinyin(source);

                for (int i = 0; i < source.length(); i++) {
                    char c = source.charAt(i);
                    //keep original alphabet
                    if (c < 128) {
                        if ((c > 96 && c < 123) || (c > 64 && c < 91) || (c > 47 && c < 58)) {
                            if (config.keepNoneChinese) {
                                candidate.add(String.valueOf(c));
                                firstLetters.append(c);
                            }
                        }
                    } else {

                        String pinyin = pinyinList.get(i);
                        if (pinyin != null) {

                            firstLetters.append(pinyin.charAt(0));

                            if (config.keepFullPinyin) {
                                candidate.add(pinyin);
                            }
                        }
                    }
                }

            }


            if (position < candidate.size()) {
                String s = candidate.get(position);
                if (config.lowercase) {
                    s = s.toLowerCase();
                }
                termAtt.setEmpty();
                termAtt.append(s);
                offsetAtt.setOffset(correctOffset(position), correctOffset(position + 1));
                position++;
                return true;
            }


            if (config.keepOriginal && !processedOriginal) {
                processedOriginal = true;
                termAtt.setEmpty();
                termAtt.append(source);
                offsetAtt.setOffset(correctOffset(0), correctOffset(lastPosition));
                return true;
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
                termAtt.setEmpty();
                termAtt.append(fl);
                offsetAtt.setOffset(correctOffset(0), correctOffset(candidate.size()));
                return true;
            }

            done = true;
            return false;
        }
        return false;
    }

    @Override
    public final void end() throws IOException {
        super.end();
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        position = 0;
        lastPosition = 0;
        candidate.clear();
        this.done = false;
        this.processedCandidate = false;
        this.processedFirstLetter = false;
        this.processedOriginal = false;
        firstLetters = new StringBuilder();
        source = null;
    }


}
