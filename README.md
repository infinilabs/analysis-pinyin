Pinyin Analysis for ElasticSearch
==================================

The Pinyin Analysis plugin integrates Pinyin4j(http://pinyin4j.sourceforge.net/) module into elasticsearch.

    --------------------------------------------------
    | Pinyin4j   Analysis Plugin    | ElasticSearch  |
    --------------------------------------------------
    | master                        | 1.7.3 -> master|
    --------------------------------------------------
    | 1.3.3                         | 1.7.3          |
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

1.Create a index with custom pinyin analyzer

curl -XPUT http://localhost:9200/medcl/ -d'
{
    "index" : {
        "analysis" : {
            "analyzer" : {
                "pinyin_analyzer" : {
                    "tokenizer" : "my_pinyin"
                    }
            },
            "tokenizer" : {
                "my_pinyin" : {
                    "type" : "pinyin",
                    "keep_separate_first_letter" : false,
                    "keep_full_pinyin" : true,
                    "keep_original" : true,
                    "limit_first_letter_length" : 16,
                    "lowercase" : true
                }
            }
        }
    }
}'

2.Test Analyzer, analyzing a chinese name, such as 刘德华

http://localhost:9200/medcl/_analyze?text=%e5%88%98%e5%be%b7%e5%8d%8e&analyzer=pinyin_analyzer
{
  "tokens" : [
    {
      "token" : "liu",
      "start_offset" : 0,
      "end_offset" : 1,
      "type" : "word",
      "position" : 0
    },
    {
      "token" : "de",
      "start_offset" : 1,
      "end_offset" : 2,
      "type" : "word",
      "position" : 1
    },
    {
      "token" : "hua",
      "start_offset" : 2,
      "end_offset" : 3,
      "type" : "word",
      "position" : 2
    },
    {
      "token" : "刘德华",
      "start_offset" : 0,
      "end_offset" : 3,
      "type" : "word",
      "position" : 3
    },
    {
      "token" : "ldh",
      "start_offset" : 0,
      "end_offset" : 3,
      "type" : "word",
      "position" : 4
    }
  ]
}


3.Create mapping

curl -XPOST http://localhost:9200/medcl/folks/_mapping -d'
{
    "folks": {
        "properties": {
            "name": {
                "type": "multi_field",
                "fields": {
                "name": {
                        "type": "string",
                        "store": "no",
                        "term_vector": "with_positions_offsets",
                        "analyzer": "pinyin_analyzer",
                        "boost": 10
                    },
                    "pinyin": {
                        "type": "string",
                        "store": "no",
                        "term_vector": "with_positions_offsets",
                        "analyzer": "pinyin_analyzer",
                        "boost": 10
                    }
                }
            }
        }
    }
}'

4.Indexing

curl -XPOST http://localhost:9200/medcl/folks/andy -d'{"name":"刘德华"}'

5.Let's search

curl http://localhost:9200/medcl/folks/_search?q=name:%E5%88%98%E5%BE%B7%E5%8D%8E
curl http://localhost:9200/medcl/folks/_search?q=name.pinyin:%e5%88%98%e5%be%b7
curl http://localhost:9200/medcl/folks/_search?q=name.pinyin:liu
curl http://localhost:9200/medcl/folks/_search?q=name.pinyin:ldh
curl http://localhost:9200/medcl/folks/_search?q=name.pinyin:de+hua

6.Using Pinyin-TokenFilter

curl -XPUT http://localhost:9200/medcl1/ -d'
{
    "index" : {
        "analysis" : {
            "analyzer" : {
                "user_name_analyzer" : {
                    "tokenizer" : "whitespace",
                    "filter" : "pinyin_first_letter_and_full_pinyin_filter"
                }
            },
            "filter" : {
                "pinyin_first_letter_and_full_pinyin_filter" : {
                    "type" : "pinyin",
                    "keep_first_letter" : true,
                    "keep_full_pinyin" : false,
                    "keep_none_chinese" : true,
                    "keep_original" : false,
                    "limit_first_letter_length" : 16,
                    "lowercase" : true,
                    "trim_whitespace" : true,
                    "keep_none_chinese_in_first_letter" : true
                }
            }
        }
    }
}'
Token Test:刘德华 张学友 郭富城 黎明 四大天王

curl -XGET http://localhost:9200/medcl1/_analyze?text=%e5%88%98%e5%be%b7%e5%8d%8e+%e5%bc%a0%e5%ad%a6%e5%8f%8b+%e9%83%ad%e5%af%8c%e5%9f%8e+%e9%bb%8e%e6%98%8e+%e5%9b%9b%e5%a4%a7%e5%a4%a9%e7%8e%8b&analyzer=user_name_analyzer
{
  "tokens" : [
    {
      "token" : "ldh",
      "start_offset" : 0,
      "end_offset" : 3,
      "type" : "word",
      "position" : 0
    },
    {
      "token" : "zxy",
      "start_offset" : 4,
      "end_offset" : 7,
      "type" : "word",
      "position" : 1
    },
    {
      "token" : "gfc",
      "start_offset" : 8,
      "end_offset" : 11,
      "type" : "word",
      "position" : 2
    },
    {
      "token" : "lm",
      "start_offset" : 12,
      "end_offset" : 14,
      "type" : "word",
      "position" : 3
    },
    {
      "token" : "sdtw",
      "start_offset" : 15,
      "end_offset" : 19,
      "type" : "word",
      "position" : 4
    }
  ]
}

7.That's all, have fun.