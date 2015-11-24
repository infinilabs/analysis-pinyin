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
import org.apache.lucene.analysis.util.CharacterUtils;
import org.apache.lucene.util.AttributeFactory;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinyinAbbreviationsTokenizer extends Tokenizer {


    private static final int DEFAULT_BUFFER_SIZE = 256;
    private static Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]$");
    private final CharTermAttribute termAtt = (CharTermAttribute) this.addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = (OffsetAttribute) this.addAttribute(OffsetAttribute.class);
    private final CharacterUtils charUtils = CharacterUtils.getInstance();
    private final CharacterUtils.CharacterBuffer ioBuffer = CharacterUtils.newCharacterBuffer(4096);
    private final ESLogger logger = ESLoggerFactory.getLogger("analysis-pinyin");
    private int offset = 0;
    private int bufferIndex = 0;
    private int dataLen = 0;
    private int finalOffset = 0;
    private HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

    public PinyinAbbreviationsTokenizer() {
        this(DEFAULT_BUFFER_SIZE);
    }

    public PinyinAbbreviationsTokenizer(int bufferSize) {
        super();
        termAtt.resizeBuffer(bufferSize);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    public PinyinAbbreviationsTokenizer(AttributeFactory factory) {
        super(factory);
    }

    public final boolean incrementToken() throws IOException {
        this.clearAttributes();
        int length = 0;
        int start = -1;
        int end = -1;
        char[] buffer = this.termAtt.buffer();

        while (true) {
            if (this.bufferIndex >= this.dataLen) {
                this.offset += this.dataLen;
                this.charUtils.fill(this.ioBuffer, this.input);
                if (this.ioBuffer.getLength() == 0) {
                    this.dataLen = 0;
                    if (length <= 0) {
                        this.finalOffset = this.correctOffset(this.offset);
                        return false;
                    }
                    break;
                }

                this.dataLen = this.ioBuffer.getLength();
                this.bufferIndex = 0;
            }

            int c = this.charUtils.codePointAt(this.ioBuffer.getBuffer(), this.bufferIndex, this.ioBuffer.getLength());
            int charCount = Character.charCount(c);
            this.bufferIndex += charCount;
            if (this.isTokenChar(c)) {
                if (length == 0) {
                    assert start == -1;

                    start = this.offset + this.bufferIndex - charCount;
                    end = start;
                } else if (length >= buffer.length - 1) {
                    buffer = this.termAtt.resizeBuffer(2 + length);
                }

                int normResult = this.normalize(c);
                termAtt.append(String.valueOf(Character.toChars(normResult)));

                end += charCount;
                length += Character.toChars(normResult, buffer, length);
                if (length >= 255) {
                    break;
                }
            } else if (length > 0) {
                break;
            }
        }

        this.termAtt.setLength(length);

        assert start != -1;

        this.offsetAtt.setOffset(this.correctOffset(start), this.finalOffset = this.correctOffset(end));
        return true;
    }

    public final void end() throws IOException {
        super.end();
        this.offsetAtt.setOffset(this.finalOffset, this.finalOffset);
    }

    public void reset() throws IOException {
        super.reset();
        this.bufferIndex = 0;
        this.offset = 0;
        this.dataLen = 0;
        this.finalOffset = 0;
        this.ioBuffer.reset();
    }

    public boolean isTokenChar(int c) {
        Matcher matcher = pattern.matcher(String.valueOf(c));
        return Character.isLetterOrDigit(c) || matcher.matches();
    }

    protected int normalize(int c) {
        try {
            String[] strs = PinyinHelper.toHanyuPinyinStringArray((char) c, format);
            if (strs != null) {
                return strs[0].codePointAt(0);
            }
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            logger.error("pinyin", badHanyuPinyinOutputFormatCombination);
        }
        return c;
    }

}
