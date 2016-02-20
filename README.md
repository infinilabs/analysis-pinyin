Pinyin Analysis for ElasticSearch
==================================

The Pinyin Analysis plugin integrates Pinyin4j(http://pinyin4j.sourceforge.net/) module into elasticsearch.

Pinyin4j is a popular Java library supporting convertion between Chinese characters and most popular Pinyin systems. The output format of pinyin could be customized.

    --------------------------------------------------
    | Pinyin4j   Analysis Plugin    | ElasticSearch  |
    --------------------------------------------------
    | master                        | 2.2.x -> master|
    --------------------------------------------------
    | 1.6.0                         | 2.2.0          |
    --------------------------------------------------
    | 1.5.0                         | 2.1.0          |
    --------------------------------------------------
    | 1.4.0                         | 2.0.x          |
    --------------------------------------------------
    | 1.3.0                         | 1.6.x          |
    --------------------------------------------------
    | 1.2.2                         | 1.0.x          |
    --------------------------------------------------
    | 1.2.0                         | 0.90.x         |
    --------------------------------------------------
    | 1.1.2                         | 0.20.x         |
    --------------------------------------------------
    | 1.1.1                         | 0.19.x         |
    --------------------------------------------------

The plugin includes two analyzers: `pinyin` and  `pinyin_first_letter`  , two tokenizers: `pinyin` and `pinyin_first_letter` and two token-filters:  `pinyin` and  `pinyin_first_letter`.

1.Create a index for doing some tests
<pre>
curl -XPUT http://localhost:9200/medcl/ -d'
{
    "index" : {
        "analysis" : {
            "analyzer" : {
                "pinyin_analyzer" : {
                    "tokenizer" : "my_pinyin",
                    "filter" : "word_delimiter"
                    }
            },
            "tokenizer" : {
                "my_pinyin" : {
                    "type" : "pinyin",
                    "first_letter" : "none",
                    "padding_char" : " "
                }
            }
        }
    }
}'
</pre>

2.Analyzing a chinese name, such as 刘德华
<pre>
http://localhost:9200/medcl/_analyze?text=%e5%88%98%e5%be%b7%e5%8d%8e&analyzer=pinyin_analyzer
{"tokens":[{"token":"liu de hua ","start_offset":0,"end_offset":3,"type":"word","position":1}]}
</pre>

3.That's all, have fun.

optional config:
the parameter `first_letter` can be set to: `prefix`, `append`, `only` and `none`, default value is `none`

examples:
`first_letter` set to`prifix` and  `padding_char` is set to `""`
the analysis result will be:
<pre>
{"tokens":[{"token":"ldhliudehua","start_offset":0,"end_offset":3,"type":"word","position":1}]}
</pre>

and if we set `first_letter`  to `only` ,the result will be:
<pre>
{"tokens":[{"token":"ldh","start_offset":0,"end_offset":3,"type":"word","position":1}]}
</pre>
also   `first_letter`  to `append`
<pre>
{"tokens":[{"token":"liu de hua ldh","start_offset":0,"end_offset":3,"type":"word","position":1}]}
</pre>



----------additional----------example-----------------------

if you wanna do a auto-complete with people's name,combining with the magic of pinyin,and it's very easy now,here is the detail instructions:

1.Index setting
<pre>
curl -XPOST http://localhost:9200/medcl/_close
curl -XPUT http://localhost:9200/medcl/_settings -d'
{
    "index" : {
        "analysis" : {
            "analyzer" : {
                "pinyin_analyzer" : {
                    "tokenizer" : "my_pinyin",
                    "filter" : ["word_delimiter","nGram"]
                }
            },
            "tokenizer" : {
                "my_pinyin" : {
                    "type" : "pinyin",
                    "first_letter" : "prefix",
                    "padding_char" : " "
                }
            }
        }
    }
}'
curl -XPOST http://localhost:9200/medcl/_open
</pre>

2.Create mapping
<pre>
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
                    "primitive": {
                        "type": "string",
                        "store": "yes",
                        "analyzer": "keyword"
                    }
                }
            }
        }
    }
}'
</pre>

3.Indexing
<pre>
curl -XPOST http://localhost:9200/medcl/folks/andy -d'{"name":"刘德华"}'
</pre>

4.Have a try
<pre>
curl http://localhost:9200/medcl/folks/_search?q=name:%e5%88%98
curl http://localhost:9200/medcl/folks/_search?q=name:%e5%88%98%e5%be%b7
curl http://localhost:9200/medcl/folks/_search?q=name:liu
curl http://localhost:9200/medcl/folks/_search?q=name:ldh
curl http://localhost:9200/medcl/folks/_search?q=name:dehua
</pre>

5.Use Pinyin-TokenFilter (contributed by @wangweiwei)
<pre>
curl -XPUT http://localhost:9200/medcl1/ -d'
{
    "index" : {
        "analysis" : {
            "analyzer" : {
                "user_name_analyzer" : {
                    "tokenizer" : "whitespace",
                    "filter" : "pinyin_filter"
                }
            },
            "filter" : {
                "pinyin_filter" : {
                    "type" : "pinyin",
                    "first_letter" : "only",
                    "padding_char" : ""
                }
            }
        }
    }
}'
</pre>

Token Test:刘德华 张学友 郭富城 黎明 四大天王
<pre>
curl -XGET http://localhost:9200/medcl/_analyze?text=%e5%88%98%e5%be%b7%e5%8d%8e+%e5%bc%a0%e5%ad%a6%e5%8f%8b+%e9%83%ad%e5%af%8c%e5%9f%8e+%e9%bb%8e%e6%98%8e+%e5%9b%9b%e5%a4%a7%e5%a4%a9%e7%8e%8b&analyzer=user_name_analyzer
{"tokens":[{"token":"ldh","start_offset":0,"end_offset":3,"type":"word","position":1},{"token":"zxy","start_offset":4,"end_offset":7,"type":"word","position":2},{"token":"gfc","start_offset":8,"end_offset":11,"type":"word","position":3},{"token":"lm","start_offset":12,"end_offset":14,"type":"word","position":4},{"token":"sdtw","start_offset":15,"end_offset":19,"type":"word","position":5}]}
</pre>
