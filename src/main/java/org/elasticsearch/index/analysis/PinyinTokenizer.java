package org.elasticsearch.index.analysis;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Created by IntelliJ IDEA. User: Medcl' Date: 12-5-21 Time: 下午5:53
 */
public class PinyinTokenizer extends Tokenizer {

    private static final int DEFAULT_BUFFER_SIZE = 256;

    private List<String> array = null;
    private Iterator<String> tokenIter = null;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    private String mode;

    public PinyinTokenizer(Reader reader, String mode) {
        this(reader, DEFAULT_BUFFER_SIZE);
        this.mode = mode;
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
        if (tokenIter == null || !tokenIter.hasNext()) {
            int upto = 0;
            char[] buffer = termAtt.buffer();
            while (true) {
                final int length = input.read(buffer, upto, buffer.length - upto);
                if (length == -1) break;
                upto += length;
                if (upto == buffer.length) buffer = termAtt.resizeBuffer(1 + buffer.length);
            }
            if (upto == 0) return false;
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
            if (mode.equals("all")) {
                array = new ArrayList<String>();
                array.add(str);
                array.addAll(PinyinUtil.exchange(tmpFirst));
                array.addAll(PinyinUtil.exchange(tmpFull));
            } else if (mode.equals("full_only")) {
                array = PinyinUtil.exchange(tmpFull);
            } else if (mode.equals("first_only")) {
                array = PinyinUtil.exchange(tmpFirst);
            } else {
                array = new ArrayList<String>();
                array.addAll(PinyinUtil.exchange(tmpFirst));
                array.addAll(PinyinUtil.exchange(tmpFull));
            }
            tokenIter = array.iterator();

            if (!tokenIter.hasNext()) return false;
        }

        clearAttributes();

        String word = tokenIter.next();
        termAtt.copyBuffer(word.toCharArray(), 0, word.length());
        return true;
    }

    public void reset() throws IOException {
        super.reset();
        this.tokenIter = null;
    }


}
