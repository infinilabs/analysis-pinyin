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

The plugin includes a `pinyin` analyzer and two tokenizer: `pinyin` and `pinyin_first_letter`.

1.Create a index for doing some tests
<pre>
curl -XPUT http://localhost:9200/medcl/ -d'
{
    "index" : {
        "analysis" : {
            "analyzer" : {
                "pinyin_analyzer" : {
                    "tokenizer" : "my_pinyin",
                    "filter" : ["standard"]
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

2.Analyzing a chinese name,such as 刘德华
<pre>
http://localhost:9200/medcl/_analyze?text=%e5%88%98%e5%be%b7%e5%8d%8e&analyzer=pinyin_analyzer
{"tokens":[{"token":"liu de hua ","start_offset":0,"end_offset":3,"type":"word","position":1}]}
</pre>

3.Thant's all,have fun.

optional config:
the parameter `first_letter` can be set to: `prefix` , `append` , `only` and `none` ,default value is `none`

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
                    "tokenizer" : ["my_pinyin"],
                    "filter" : ["standard","nGram"]
                }
            },
            "tokenizer" : {
                "my_pinyin" : {
                    "type" : "pinyin",
                    "first_letter" : "prefix",
                    "padding_char" : ""
                }
            }
        }
    }
}'
curl -XPOST http://localhost:9200/medcl/_close
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