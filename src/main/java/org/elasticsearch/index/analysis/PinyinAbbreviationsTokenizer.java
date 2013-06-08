package org.elasticsearch.index.analysis;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.Version;

import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinyinAbbreviationsTokenizer extends CharTokenizer {

    private static final int DEFAULT_BUFFER_SIZE = 256;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    private static Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]$");

    public PinyinAbbreviationsTokenizer(Reader reader) {
        this(reader, DEFAULT_BUFFER_SIZE);
    }

    public PinyinAbbreviationsTokenizer(Reader input, int bufferSize) {
        super(Version.LUCENE_41,input);
        termAtt.resizeBuffer(bufferSize);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }


    @Override
    public boolean isTokenChar(int c){
        Matcher matcher = pattern.matcher(String.valueOf(c));
            return  Character.isLetterOrDigit(c)|| matcher.matches();
    }

  @Override
  protected int normalize(int c) {
       try {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray((char) c, format);
                    if (strs != null) {
                        termAtt.append(strs[0]);
                       return  strs[0].codePointAt(0);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
      return c;
  }
}
