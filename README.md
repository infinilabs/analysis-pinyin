Pinyin Analysis for ElasticSearch
==================================

The Pinyin Analysis plugin integrates Pinyin4j(http://pinyin4j.sourceforge.net/) module into elasticsearch.

    --------------------------------------------------
    | Pinyin4j   Analysis Plugin    | ElasticSearch  |
    --------------------------------------------------
    | master                        | 1.7.0 -> master|
    --------------------------------------------------
    | 1.3.1                         | 1.7.0          |
    --------------------------------------------------
    | 1.3.0                         | 1.6.0          |
    --------------------------------------------------
    | 1.2.2                         | 1.0.0          |
    --------------------------------------------------
    | 1.2.0                         | 0.90.0         |
    --------------------------------------------------
    | 1.1.2                         | 0.20.2         |
    --------------------------------------------------
    | 1.1.1                         | 0.19.x         |
    --------------------------------------------------
    | 1.1.0                         | 0.19.0         |
    --------------------------------------------------

The plugin includes analyzer: `pinyin` ,  tokenizer: `pinyin` and  token-filter:  `pinyin`.

** Optional Parameters ** 
* `remove_duplicated_term` when this option enabled, duplicated term will be removed to save index, eg: `de的`>`de`, default: false,  NOTE: position related query maybe influenced
* `keep_first_letter` when this option enabled,  eg: `刘德华`>`ldh`, default: true
* `keep_separate_first_letter` when this option enabled, will keep first letters separately,  eg: `刘德华`>`l`,`d`,`h`, default: false, NOTE: query result maybe too fuzziness due to term too frequency
* `limit_first_letter_length` set max length of the first_letter result, default: 16
* `keep_full_pinyin` when this option enabled, eg: `刘德华`> [`liu`,`de`,`hua`], default: true
* `keep_joined_full_pinyin` when this option enabled, eg: `刘德华`> [`liudehua`], default: false
* `keep_none_chinese` keep non chinese letter or number in result, default: true
* `keep_none_chinese_together` keep non chinese letter together, default: true, eg: `DJ音乐家` -> `DJ`,`yin`,`yue`,`jia`, when set to `false`, eg: `DJ音乐家` -> `D`,`J`,`yin`,`yue`,`jia`, NOTE: `keep_none_chinese` should be enabled first
* `keep_none_chinese_in_first_letter` keep non Chinese letters in first letter, eg: `刘德华AT2016`->`ldhat2016`, default: true
* `none_chinese_pinyin_tokenize` break non chinese letters into separate pinyin term if they are pinyin, default: true, eg: `liudehuaalibaba13zhuanghan` -> `liu`,`de`,`hua`,`a`,`li`,`ba`,`ba`,`13`,`zhuang`,`han`, NOTE:  `keep_none_chinese` and `keep_none_chinese_together` should be enabled first
* `keep_original` when this option enabled, will keep original input as well, default: false
* `lowercase`  lowercase non Chinese letters, default: true
* `trim_whitespace` default: true
