Pinyin Analysis for Elasticsearch
==================================

This Pinyin Analysis plugin is used to do conversion between Chinese characters and Pinyin, integrates NLP tools (https://github.com/NLPchina/nlp-lang).

    --------------------------------------------------
    | Pinyin Analysis Plugin        | Elasticsearch  |
    --------------------------------------------------
    | master                        | 7.x -> master  |
    --------------------------------------------------
    | 6.x                           | 6.x            |
    --------------------------------------------------  
    | 5.x                           | 5.x            |
    --------------------------------------------------  
    | 1.8.1                         | 2.4.1          |
    --------------------------------------------------  
    | 1.7.5                         | 2.3.5          |
    --------------------------------------------------  
    | 1.6.1                         | 2.2.1          |
    --------------------------------------------------
    | 1.5.0                         | 2.1.0          |
    --------------------------------------------------
    | 1.4.0                         | 2.0.x          |
    --------------------------------------------------
    | 1.3.0                         | 1.6.x          |
    --------------------------------------------------
    | 1.2.2                         | 1.0.x          |
    --------------------------------------------------

The plugin includes analyzer: `pinyin` ,  tokenizer: `pinyin` and  token-filter:  `pinyin`.

** Optional Parameters ** 
* `keep_first_letter` when this option enabled,  eg: `刘德华`>`ldh`, default: true
* `keep_separate_first_letter` when this option enabled, will keep first letters separately,  eg: `刘德华`>`l`,`d`,`h`, default: false, NOTE: query result maybe too fuzziness due to term too frequency
* `limit_first_letter_length` set max length of the first_letter result, default: 16
* `keep_full_pinyin` when this option enabled, eg: `刘德华`> [`liu`,`de`,`hua`], default: true
* `keep_joined_full_pinyin` when this option enabled, eg: `刘德华`> [`liudehua`], default: false
* `keep_none_chinese` keep non chinese letter or number in result, default: true
* `keep_none_chinese_together` keep non chinese letter together, default: true, eg: `DJ音乐家` -> `DJ`,`yin`,`yue`,`jia`, when set to `false`, eg: `DJ音乐家` -> `D`,`J`,`yin`,`yue`,`jia`, NOTE: `keep_none_chinese` should be enabled first
* `keep_none_chinese_in_first_letter` keep non Chinese letters in first letter, eg: `刘德华AT2016`->`ldhat2016`, default: true
* `keep_none_chinese_in_joined_full_pinyin` keep non Chinese letters in joined full pinyin, eg: `刘德华2016`->`liudehua2016`, default: false
* `none_chinese_pinyin_tokenize` break non chinese letters into separate pinyin term if they are pinyin, default: true, eg: `liudehuaalibaba13zhuanghan` -> `liu`,`de`,`hua`,`a`,`li`,`ba`,`ba`,`13`,`zhuang`,`han`, NOTE:  `keep_none_chinese` and `keep_none_chinese_together` should be enabled first
* `keep_original` when this option enabled, will keep original input as well, default: false
* `lowercase`  lowercase non Chinese letters, default: true
* `trim_whitespace` default: true
* `remove_duplicated_term` when this option enabled, duplicated term will be removed to save index, eg: `de的`>`de`, default: false,  NOTE: position related query maybe influenced
* `ignore_pinyin_offset` after 6.0, offset is strictly constrained, overlapped tokens are not allowed, with this parameter, overlapped token will allowed by ignore offset, please note, all position related query or highlight will become incorrect, you should use multi fields and specify different settings for different query purpose. if you need offset, please set it to false. default: true.



1.Create a index with custom pinyin analyzer
<pre>
PUT /medcl/ 
{
    "settings" : {
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
                    "lowercase" : true,
                    "remove_duplicated_term" : true
                }
            }
        }
    }
}
</pre>

2.Test Analyzer, analyzing a chinese name, such as 刘德华
<pre>
GET /medcl/_analyze
{
  "text": ["刘德华"],
  "analyzer": "pinyin_analyzer"
}</pre>
<pre>
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
</pre>

3.Create mapping
<pre>
POST /medcl/_mapping 
{
        "properties": {
            "name": {
                "type": "keyword",
                "fields": {
                    "pinyin": {
                        "type": "text",
                        "store": false,
                        "term_vector": "with_offsets",
                        "analyzer": "pinyin_analyzer",
                        "boost": 10
                    }
                }
            }
        }
    
}
</pre>

4.Indexing
<pre>
POST /medcl/_create/andy
{"name":"刘德华"}
</pre>

5.Let's search

<pre>

curl http://localhost:9200/medcl/_search?q=name:%E5%88%98%E5%BE%B7%E5%8D%8E
curl http://localhost:9200/medcl/_search?q=name.pinyin:%e5%88%98%e5%be%b7
curl http://localhost:9200/medcl/_search?q=name.pinyin:liu
curl http://localhost:9200/medcl/_search?q=name.pinyin:ldh
curl http://localhost:9200/medcl/_search?q=name.pinyin:de+hua

</pre>

6.Using Pinyin-TokenFilter
<pre>
PUT /medcl1/ 
{
    "settings" : {
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
}
</pre>

Token Test:刘德华 张学友 郭富城 黎明 四大天王
<pre>
GET /medcl1/_analyze
{
  "text": ["刘德华 张学友 郭富城 黎明 四大天王"],
  "analyzer": "user_name_analyzer"
}
</pre>
<pre>
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
</pre>


7.Used in phrase query

- option 1

<pre>
PUT /medcl2/
{
    "settings" : {
        "analysis" : {
            "analyzer" : {
                "pinyin_analyzer" : {
                    "tokenizer" : "my_pinyin"
                    }
            },
            "tokenizer" : {
                "my_pinyin" : {
                    "type" : "pinyin",
                    "keep_first_letter":false,
                    "keep_separate_first_letter" : false,
                    "keep_full_pinyin" : true,
                    "keep_original" : false,
                    "limit_first_letter_length" : 16,
                    "lowercase" : true
                }
            }
        }
    }
}
GET /medcl2/_search
{
  "query": {"match_phrase": {
    "name.pinyin": "刘德华"
  }}
}

</pre>

- option 2

<pre>
 
PUT /medcl3/
{
   "settings" : {
       "analysis" : {
           "analyzer" : {
               "pinyin_analyzer" : {
                   "tokenizer" : "my_pinyin"
                   }
           },
           "tokenizer" : {
               "my_pinyin" : {
                   "type" : "pinyin",
                   "keep_first_letter":true,
                   "keep_separate_first_letter" : true,
                   "keep_full_pinyin" : true,
                   "keep_original" : false,
                   "limit_first_letter_length" : 16,
                   "lowercase" : true
               }
           }
       }
   }
}
   
POST /medcl3/_mapping 
{
  "properties": {
      "name": {
          "type": "keyword",
          "fields": {
              "pinyin": {
                  "type": "text",
                  "store": false,
                  "term_vector": "with_offsets",
                  "analyzer": "pinyin_analyzer",
                  "boost": 10
              }
          }
      }
  }
}
  
   
GET /medcl3/_analyze
{
   "text": ["刘德华"],
   "analyzer": "pinyin_analyzer"
}
 
POST /medcl3/_create/andy
{"name":"刘德华"}

GET /medcl3/_search
{
 "query": {"match_phrase": {
   "name.pinyin": "刘德h"
 }}
}

GET /medcl3/_search
{
 "query": {"match_phrase": {
   "name.pinyin": "刘dh"
 }}
}

GET /medcl3/_search
{
 "query": {"match_phrase": {
   "name.pinyin": "liudh"
 }}
}

GET /medcl3/_search
{
 "query": {"match_phrase": {
   "name.pinyin": "liudeh"
 }}
}

GET /medcl3/_search
{
 "query": {"match_phrase": {
   "name.pinyin": "liude华"
 }}
}

</pre>

8.That's all, have fun.
