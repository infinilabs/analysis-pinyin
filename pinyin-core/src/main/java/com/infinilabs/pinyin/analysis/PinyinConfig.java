package com.infinilabs.pinyin.analysis;

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
    
    
}
