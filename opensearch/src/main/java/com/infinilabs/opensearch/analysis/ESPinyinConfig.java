package com.infinilabs.opensearch.analysis;

import com.infinilabs.pinyin.analysis.PinyinConfig;
import org.opensearch.common.settings.Settings;

public class ESPinyinConfig extends PinyinConfig {
  public ESPinyinConfig() {
  }

  public ESPinyinConfig(Settings settings) {
    this.keepFirstLetter = settings.getAsBoolean("keep_first_letter", true);
    this.keepSeparateFirstLetter = settings.getAsBoolean("keep_separate_first_letter", false);
    this.keepFullPinyin = settings.getAsBoolean("keep_full_pinyin", true);
    this.keepJoinedFullPinyin = settings.getAsBoolean("keep_joined_full_pinyin", false);
    this.keepNoneChinese = settings.getAsBoolean("keep_none_chinese", true);
    this.keepNoneChineseTogether = settings.getAsBoolean("keep_none_chinese_together", true);
    this.noneChinesePinyinTokenize = settings.getAsBoolean("none_chinese_pinyin_tokenize", true);
    this.keepOriginal = settings.getAsBoolean("keep_original", false);
    this.LimitFirstLetterLength = settings.getAsInt("limit_first_letter_length", 16);
    this.lowercase = settings.getAsBoolean("lowercase", true);
    this.trimWhitespace = settings.getAsBoolean("trim_whitespace", true);
    this.keepNoneChineseInFirstLetter = settings.getAsBoolean("keep_none_chinese_in_first_letter", true);
    this.keepNoneChineseInJoinedFullPinyin = settings.getAsBoolean("keep_none_chinese_in_joined_full_pinyin", false);
    this.removeDuplicateTerm = settings.getAsBoolean("remove_duplicated_term", false);
    this.fixedPinyinOffset = settings.getAsBoolean("fixed_pinyin_offset", false);
    this.ignorePinyinOffset = settings.getAsBoolean("ignore_pinyin_offset", true);
    this.keepSeparateChinese = settings.getAsBoolean("keep_separate_chinese", false);
  }
}
