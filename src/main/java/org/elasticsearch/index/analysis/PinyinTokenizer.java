package org.elasticsearch.index.analysis;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

/**
 * Created by IntelliJ IDEA. User: Medcl' Date: 12-5-21 Time: 下午5:53
 */
public class PinyinTokenizer extends Tokenizer {

    private static final int DEFAULT_BUFFER_SIZE = 256;

    private boolean done = false;
    private int finalOffset;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    private String padding_char;
    private String first_letter;

    public PinyinTokenizer(Reader reader, String padding_char, String first_letter) {
        this(reader, DEFAULT_BUFFER_SIZE);
        this.padding_char = padding_char;
        this.first_letter = first_letter;
    }

    public PinyinTokenizer(Reader input, int bufferSize) {
        super(input);
        termAtt.resizeBuffer(bufferSize);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    @Override
    public final boolean incrementToken() throws IOException {
        clearAttributes();

        if (!done) {
            done = true;
            int upto = 0;
            char[] buffer = termAtt.buffer();
            while (true) {
                final int length = input.read(buffer, upto, buffer.length - upto);
                if (length == -1) break;
                upto += length;
                if (upto == buffer.length) buffer = termAtt.resizeBuffer(1 + buffer.length);
            }
            termAtt.setLength(upto);
            String str = termAtt.toString();
            termAtt.setEmpty();
            String[][] tmpFull = new String[upto][];
            String[][] tmpFirst = new String[upto][];
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                // 是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
                try {
                    tmpFull[i] = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (tmpFull[i] != null) {
                        tmpFull[i] = PinyinUtil.removeDuplicates(tmpFull[i]);
                        List<String> firstLetters = new ArrayList<String>();
                        for (String py : tmpFull[i]) {
                            String firstLetter = py.substring(0, 1);
                            if (!firstLetters.contains(firstLetter)) firstLetters.add(firstLetter);
                        }
                        tmpFirst[i] = firstLetters.toArray(new String[firstLetters.size()]);
                    } else {
                        tmpFull[i] = new String[] {String.valueOf(c)};
                        tmpFirst[i] = new String[] {String.valueOf(c)};
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }

            // let's join them
            if (first_letter.equals("all")) {
                termAtt.append(str).append(this.padding_char);
                termAtt.append(PinyinUtil.exchange(tmpFirst, this.padding_char));
                if (this.padding_char.length() > 0) {
                    termAtt.append(this.padding_char);
                }
                termAtt.append(PinyinUtil.exchange(tmpFull, this.padding_char));
            } else if (first_letter.equals("prefix")) {
                termAtt.append(PinyinUtil.exchange(tmpFirst, this.padding_char));
                if (this.padding_char.length() > 0) {
                    termAtt.append(this.padding_char);
                }
                termAtt.append(PinyinUtil.exchange(tmpFull, this.padding_char));
            } else if (first_letter.equals("append")) {
                String full = PinyinUtil.exchange(tmpFull, this.padding_char);
                termAtt.append(full);
                if (this.padding_char.length() > 0) {
                    if (!full.endsWith(this.padding_char)) {
                        termAtt.append(this.padding_char);
                    }
                }
                termAtt.append(PinyinUtil.exchange(tmpFirst, this.padding_char));
            } else if (first_letter.equals("none")) {
                termAtt.append(PinyinUtil.exchange(tmpFull, this.padding_char));
            } else if (first_letter.equals("only")) {
                termAtt.append(PinyinUtil.exchange(tmpFirst, this.padding_char));
            }


            finalOffset = correctOffset(upto);
            offsetAtt.setOffset(correctOffset(0), finalOffset);
            return true;
        }
        return false;
    }

    @Override
    public final void end() {
        // set final offset
        offsetAtt.setOffset(finalOffset, finalOffset);
    }

    public void reset() throws IOException {
        super.reset();
        this.done = false;
    }


}
