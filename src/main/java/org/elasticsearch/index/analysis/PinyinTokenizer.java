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

import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Medcl'
 * Date: 12-5-21
 * Time: 下午5:53
 */
public class PinyinTokenizer extends Tokenizer {

    private static final int DEFAULT_BUFFER_SIZE = 256;

    private boolean done = false;
    private int finalOffset;
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    private static Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]$");
    private String padding_char;

    public PinyinTokenizer(Reader reader,String padding_char) {
        this(reader, DEFAULT_BUFFER_SIZE);
         this.padding_char = padding_char;
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
        if (!done) {
            clearAttributes();
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
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                Matcher matcher = pattern.matcher(String.valueOf(c));
                if (!matcher.matches()) {
                    termAtt.append(c);
                } else {
                    try {

                        String[] strs = PinyinHelper.toHanyuPinyinStringArray(c, format);
                        if (strs != null) {
                            termAtt.append(strs[0]);   //get first result by default
                            if(this.padding_char.length()>0)
                            {
                                termAtt.append(this.padding_char); //TODO splitter
                            }
                            //TODO more than one pinyin
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                        badHanyuPinyinOutputFormatCombination.printStackTrace();
                    }
                }
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
    public void reset(Reader input) throws IOException {
        super.reset(input);
        this.done = false;
    }


}
