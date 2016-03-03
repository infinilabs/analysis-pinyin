package org.elasticsearch.index.analysis;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Medcl'
 * Date: 12-5-21
 * Time: 下午5:53
 */
public class PinyinTokenizer extends Tokenizer {

    private static final int DEFAULT_BUFFER_SIZE = 256;
    private final ESLogger logger = ESLoggerFactory.getLogger("pinyin-analyzer");
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private boolean done = false;
    private int finalOffset;
    private OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    private String padding_char;
    private String first_letter;

    public PinyinTokenizer(String first_letter, String padding_char) {
        this(DEFAULT_BUFFER_SIZE);
        this.padding_char = padding_char;
        this.first_letter = first_letter;
    }

    public PinyinTokenizer(int bufferSize) {
        super();
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
                if (upto == buffer.length)
                    buffer = termAtt.resizeBuffer(1 + buffer.length);
            }
            termAtt.setLength(upto);
            String str = termAtt.toString();
            termAtt.setEmpty();
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder firstLetters = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c<128) {
                    if( (c > 96 && c < 123 ) || (c > 64 && c < 91 )|| (c > 47 && c < 58 )){
                        stringBuilder.append(c);
                        firstLetters.append(c);
                    }else{
                        stringBuilder.append(" ");
                    }
                } else {
                    try {
                        String[] strs = PinyinHelper.toHanyuPinyinStringArray(c, format);
                        if (strs != null) {
                            //get first result by default
                            String first_value = strs[0];
                            //TODO more than one pinyin
                            if (this.padding_char.length() > 0) {
                                if (stringBuilder.length() > 0) stringBuilder.append(this.padding_char);
                            }

                            stringBuilder.append(first_value);
                            firstLetters.append(first_value.charAt(0));

                        }
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                        logger.error("pinyin-tokenizer", badHanyuPinyinOutputFormatCombination);
                    }
                }
            }

            //let's join them
            if (first_letter.equals("prefix")) {
                termAtt.append(firstLetters.toString());
                if (this.padding_char.length() > 0) {
                    termAtt.append(this.padding_char); //TODO splitter
                }
                termAtt.append(stringBuilder.toString());
            } else if (first_letter.equals("append")) {
                termAtt.append(stringBuilder.toString());
                if (this.padding_char.length() > 0) {
                    if (!stringBuilder.toString().endsWith(this.padding_char)) {
                        termAtt.append(this.padding_char);
                    }
                }
                termAtt.append(firstLetters.toString());
            } else if (first_letter.equals("none")) {
                termAtt.append(stringBuilder.toString());
            } else if (first_letter.equals("only")) {
                termAtt.append(firstLetters.toString());
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

    @Override
    public void reset() throws IOException {
        super.reset();
        this.done = false;
    }


}
