Pinyin Analysis for ElasticSearch
==================================

The Pinyin Analysis plugin integrates Pinyin4j(http://pinyin4j.sourceforge.net/) module into elasticsearch.

Pinyin4j is a popular Java library supporting convertion between Chinese characters and most popular Pinyin systems. The output format of pinyin could be customized.

In order to install the plugin, simply run: `bin/plugin -install medcl/elasticsearch-analysis-pinyin/1.1.0`.

    --------------------------------------------------
    | Pinyin4j   Analysis Plugin    | ElasticSearch  |
    --------------------------------------------------
    | master                        | 0.19 -> master |
    --------------------------------------------------
    | 1.1.0                         | 0.19 -> master |
    --------------------------------------------------

The plugin includes the `pinyin` analyzer, `pinyin` tokenizer, and `pinyin` token filter.

1.是否移除空格
2.是否只返回拼音首字母
3.是否只处理中文
