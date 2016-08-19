package org.elasticsearch.analysis;

import org.elasticsearch.common.settings.Settings;

/**
 * Created by medcl on 15/11/26.
 */
public class PinyinConfig {

    public boolean lowercase=true;
    public boolean trimWhitespace=true;
    public boolean keepNoneChinese=true;
    public boolean keepNoneChineseInFirstLetter =true;
    public boolean keepOriginal=true;
    public boolean keepFirstLetter=true;
    public int     LimitFirstLetterLength=16;
    public boolean keepFullPinyin=true;

    public PinyinConfig() {}
    public PinyinConfig(Settings settings) {
        this.keepFirstLetter=settings.getAsBoolean("keep_first_letter",true);
        this.keepFullPinyin=settings.getAsBoolean("keep_full_pinyin", true);
        this.keepNoneChinese=settings.getAsBoolean("keep_none_chinese",true);
        this.keepOriginal=settings.getAsBoolean("keep_original", true);
        this.LimitFirstLetterLength=settings.getAsInt("limit_first_letter_length", 16);
        this.lowercase=settings.getAsBoolean("lowercase", true);
        this.trimWhitespace=settings.getAsBoolean("trim_whitespace", true);
        this.keepNoneChineseInFirstLetter =settings.getAsBoolean("keep_none_chinese_in_first_letter", true);
    }
}
