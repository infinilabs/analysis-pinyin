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
    public boolean keepNoneChineseInJoinedFullPinyin =false;
    public boolean keepOriginal=false;
    public boolean keepFirstLetter=true;
    public boolean keepSeparateFirstLetter=false;
    public boolean keepNoneChineseTogether=true;
    public boolean noneChinesePinyinTokenize =true;
    public int     LimitFirstLetterLength=16;
    public boolean keepFullPinyin=true;
    public boolean keepJoinedFullPinyin =false;
    public boolean removeDuplicateTerm=false;
    public boolean fixedPinyinOffset =false;
    //  after 6.0, offset is strictly constrained, overlapped tokens are not allowed, with this parameter, overlapped token will allowed by ignore offset, please note, all position related query or highlight will become incorrect, you should use multi fields and specify different settings for different query purpose. if you need offset, please set it to false. default: true.
    public boolean ignorePinyinOffset =true;
    public  boolean keepSeparateChinese=false;

    public PinyinConfig() {}
    public PinyinConfig(Settings settings) {
        this.keepFirstLetter=settings.getAsBoolean("keep_first_letter",true);
        this.keepSeparateFirstLetter=settings.getAsBoolean("keep_separate_first_letter",false);
        this.keepFullPinyin=settings.getAsBoolean("keep_full_pinyin", true);
        this.keepJoinedFullPinyin =settings.getAsBoolean("keep_joined_full_pinyin", false);
        this.keepNoneChinese=settings.getAsBoolean("keep_none_chinese",true);
        this.keepNoneChineseTogether=settings.getAsBoolean("keep_none_chinese_together",true);
        this.noneChinesePinyinTokenize =settings.getAsBoolean("none_chinese_pinyin_tokenize",true);
        this.keepOriginal=settings.getAsBoolean("keep_original", false);
        this.LimitFirstLetterLength=settings.getAsInt("limit_first_letter_length", 16);
        this.lowercase=settings.getAsBoolean("lowercase", true);
        this.trimWhitespace=settings.getAsBoolean("trim_whitespace", true);
        this.keepNoneChineseInFirstLetter =settings.getAsBoolean("keep_none_chinese_in_first_letter", true);
        this.keepNoneChineseInJoinedFullPinyin =settings.getAsBoolean("keep_none_chinese_in_joined_full_pinyin", false);
        this.removeDuplicateTerm =settings.getAsBoolean("remove_duplicated_term", false);
        this.fixedPinyinOffset =settings.getAsBoolean("fixed_pinyin_offset", false);
        this.ignorePinyinOffset =settings.getAsBoolean("ignore_pinyin_offset", true);
        this.keepSeparateChinese=settings.getAsBoolean("keep_separate_chinese", false);
    }
}
