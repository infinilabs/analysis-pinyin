/*
* Licensed to ElasticSearch and Shay Banon under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. ElasticSearch licenses this
* file to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.elasticsearch.index.analysis;

import junit.framework.Assert;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.elasticsearch.analysis.PinyinConfig;
import org.junit.Test;
import org.nlpcn.commons.lang.pinyin.Pinyin;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 */
public class PinyinAnalysisTests {


    @Test
    public void testTokenFilter() throws IOException {
        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepOriginal = false;
        config.keepFullPinyin = false;

        StringReader sr = new StringReader("刘德华");
        Analyzer analyzer = new StandardAnalyzer();
        PinyinTokenFilter filter = new PinyinTokenFilter(analyzer.tokenStream("f", sr), config);
        List<String> pinyin = new ArrayList<String>();
        filter.reset();
        System.out.println();
        while (filter.incrementToken()) {
            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
            pinyin.add(ta.toString());
            System.out.println(ta.toString());
        }

        Assert.assertEquals(3, pinyin.size());
        Assert.assertEquals("l", pinyin.get(0));
        Assert.assertEquals("d", pinyin.get(1));
        Assert.assertEquals("h", pinyin.get(2));

        sr = new StringReader("刘德华");
        analyzer = new KeywordAnalyzer();
        filter = new PinyinTokenFilter(analyzer.tokenStream("f", sr), config);
        pinyin.clear();
        filter.reset();
        System.out.println();
        while (filter.incrementToken()) {
            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
            pinyin.add(ta.toString());
            System.out.println(ta.toString());
        }
        Assert.assertEquals(1, pinyin.size());
        Assert.assertEquals("ldh", pinyin.get(0));


        config = new PinyinConfig();
        config.keepFirstLetter = false;
        config.keepNoneChinese = true;
        config.keepOriginal = false;
        config.keepFullPinyin = true;

        sr = new StringReader("刘德华");
        analyzer = new StandardAnalyzer();
        filter = new PinyinTokenFilter(analyzer.tokenStream("f", sr), config);
        pinyin = new ArrayList<String>();
        filter.reset();
        System.out.println();
        while (filter.incrementToken()) {
            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
            pinyin.add(ta.toString());
            System.out.println(ta.toString());
        }
        Assert.assertEquals(3, pinyin.size());
        Assert.assertEquals("liu", pinyin.get(0));
        Assert.assertEquals("de", pinyin.get(1));
        Assert.assertEquals("hua", pinyin.get(2));


        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepOriginal = true;
        config.keepFullPinyin = true;

        sr = new StringReader("刘德华");
        analyzer = new StandardAnalyzer();
        filter = new PinyinTokenFilter(analyzer.tokenStream("f", sr), config);
        pinyin = new ArrayList<String>();
        filter.reset();
        System.out.println();
        while (filter.incrementToken()) {
            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
            pinyin.add(ta.toString());
            System.out.println(ta.toString());
        }

        Assert.assertEquals(9, pinyin.size());
        Assert.assertEquals("liu", pinyin.get(0));
        Assert.assertEquals("刘", pinyin.get(1));
        Assert.assertEquals("l", pinyin.get(2));
        Assert.assertEquals("de", pinyin.get(3));
        Assert.assertEquals("德", pinyin.get(4));
        Assert.assertEquals("d", pinyin.get(5));
        Assert.assertEquals("hua", pinyin.get(6));
        Assert.assertEquals("华", pinyin.get(7));
        Assert.assertEquals("h", pinyin.get(8));


        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepOriginal = true;
        config.keepFullPinyin = true;

        sr = new StringReader("刘德华");
        analyzer = new KeywordAnalyzer();
        filter = new PinyinTokenFilter(analyzer.tokenStream("f", sr), config);
        pinyin = new ArrayList<String>();
        filter.reset();
        System.out.println();
        while (filter.incrementToken()) {
            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
            pinyin.add(ta.toString());
            System.out.println(ta.toString());
        }

        Assert.assertEquals(5, pinyin.size());
        Assert.assertEquals("liu", pinyin.get(0));
        Assert.assertEquals("de", pinyin.get(1));
        Assert.assertEquals("hua", pinyin.get(2));
        Assert.assertEquals("刘德华", pinyin.get(3));
        Assert.assertEquals("ldh", pinyin.get(4));


        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepNoneChinese = false;
        config.keepNoneChineseInFirstLetter = true;
        config.keepOriginal = false;
        config.keepFullPinyin = false;
        config.LimitFirstLetterLength = 5;
        config.lowercase = true;

        sr = new StringReader("Go的数组是纯粹的值类型，传递一个[N]T的代价是N个T");
        analyzer = new KeywordAnalyzer();
        filter = new PinyinTokenFilter(analyzer.tokenStream("f", sr), config);
        pinyin = new ArrayList<String>();
        filter.reset();
        System.out.println();
        while (filter.incrementToken()) {
            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
            pinyin.add(ta.toString());
            System.out.println(ta.toString());
        }

        Assert.assertEquals(1, pinyin.size());
        Assert.assertEquals("godsz", pinyin.get(0));


    }

    @Test
    public void TestTokenizer() throws IOException {
        String[] s =
                {"刘德华"
                        , "劉德華", "刘德华A1",
                        "讲话频率小，不能发高音", "T波低平或倒置", "β-氨基酸尿",
                        "DJ音乐家", "人生一大乐事, 哈哈"
                };

        PinyinConfig config = new PinyinConfig();
        HashMap<String, ArrayList<String>> result = getStringArrayListHashMap(s, config);

        ArrayList<String> re = result.get("刘德华");
        Assert.assertEquals(5, re.size());
        Assert.assertEquals("liu", re.get(0));
        Assert.assertEquals("de", re.get(1));
        Assert.assertEquals("hua", re.get(2));
        Assert.assertEquals("刘德华", re.get(3));
        Assert.assertEquals("ldh", re.get(4));

        re = result.get("劉德華");
        Assert.assertEquals(5, re.size());
        Assert.assertEquals("liu", re.get(0));
        Assert.assertEquals("de", re.get(1));
        Assert.assertEquals("hua", re.get(2));
        Assert.assertEquals("劉德華", re.get(3));
        Assert.assertEquals("ldh", re.get(4));

        re = result.get("刘德华A1");
        Assert.assertEquals(5, re.size());
        Assert.assertEquals("liu", re.get(0));
        Assert.assertEquals("de", re.get(1));
        Assert.assertEquals("hua", re.get(2));
        Assert.assertEquals("刘德华A1", re.get(3));
        Assert.assertEquals("ldha1", re.get(4));

        re = result.get("讲话频率小，不能发高音");
        Assert.assertEquals(12, re.size());
        Assert.assertEquals("jiang", re.get(0));
        Assert.assertEquals("hua", re.get(1));
        Assert.assertEquals("pin", re.get(2));
        Assert.assertEquals("lv", re.get(3));
        Assert.assertEquals("xiao", re.get(4));
        Assert.assertEquals("bu", re.get(5));
        Assert.assertEquals("neng", re.get(6));
        Assert.assertEquals("fa", re.get(7));
        Assert.assertEquals("gao", re.get(8));
        Assert.assertEquals("yin", re.get(9));
        Assert.assertEquals("讲话频率小，不能发高音", re.get(10));
        Assert.assertEquals("jhplxbnfgy", re.get(11));

        re = result.get("T波低平或倒置");
        Assert.assertEquals(9, re.size());
        Assert.assertEquals("t", re.get(0));
        Assert.assertEquals("bo", re.get(1));
        Assert.assertEquals("di", re.get(2));
        Assert.assertEquals("ping", re.get(3));
        Assert.assertEquals("huo", re.get(4));
        Assert.assertEquals("dao", re.get(5));
        Assert.assertEquals("zhi", re.get(6));
        Assert.assertEquals("T波低平或倒置", re.get(7));
        Assert.assertEquals("tbdphdz", re.get(8));

        re = result.get("β-氨基酸尿");
        Assert.assertEquals(6, re.size());
        Assert.assertEquals("an", re.get(0));
        Assert.assertEquals("ji", re.get(1));
        Assert.assertEquals("suan", re.get(2));
        Assert.assertEquals("niao", re.get(3));
        Assert.assertEquals("β-氨基酸尿", re.get(4));
        Assert.assertEquals("ajsn", re.get(5));

        re = result.get("DJ音乐家");
        Assert.assertEquals(6, re.size());
        Assert.assertEquals("dj", re.get(0));
        Assert.assertEquals("yin", re.get(1));
        Assert.assertEquals("yue", re.get(2));
        Assert.assertEquals("jia", re.get(3));
        Assert.assertEquals("DJ音乐家", re.get(4));
        Assert.assertEquals("djyyj", re.get(5));
    }

    @Test
    public void TestOnlyFirstLetterTokenizer() throws IOException {
        String[] s =
                {"刘德华", "β-氨基酸尿", "DJ音乐家"
                };

        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepOriginal = false;
        config.keepFullPinyin = false;
        config.keepNoneChineseTogether = false;

        HashMap<String, ArrayList<String>> result = getStringArrayListHashMap(s, config);

        ArrayList<String> re = result.get("刘德华");
        Assert.assertEquals(1, re.size());
        Assert.assertEquals("ldh", re.get(0));

        re = result.get("β-氨基酸尿");
        Assert.assertEquals(1, re.size());
        Assert.assertEquals("ajsn", re.get(0));

        re = result.get("DJ音乐家");
        Assert.assertEquals(3, re.size());
        Assert.assertEquals("d", re.get(0));
        Assert.assertEquals("j", re.get(1));
        Assert.assertEquals("djyyj", re.get(2));


        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepNoneChinese = false;
        config.keepOriginal = false;
        config.keepFullPinyin = false;
        config.keepNoneChineseTogether = false;

        result = getStringArrayListHashMap(s, config);

        re = result.get("DJ音乐家");
        Assert.assertEquals(1, re.size());
        Assert.assertEquals("yyj", re.get(0));

        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepNoneChineseTogether = true;
        config.keepOriginal = false;
        config.keepFullPinyin = false;
        result = getStringArrayListHashMap(s, config);

        re = result.get("DJ音乐家");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("dj", re.get(0));
        Assert.assertEquals("djyyj", re.get(1));
    }

    @Test
    public void TestPinyin() {
        List<String> result = Pinyin.pinyin("德");
        for (int i = 0; i < result.size(); i++) {
            String s = result.get(i);
            System.out.println(s);
        }
        Assert.assertEquals("de", result.get(0));
    }

    private HashMap<String, ArrayList<String>> getStringArrayListHashMap(String[] s, PinyinConfig config) throws IOException {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        for (String value : s) {
            System.out.println("\n" + value);
            StringReader sr = new StringReader(value);

            PinyinTokenizer tokenizer = new PinyinTokenizer(config);
            tokenizer.setReader(sr);

            tokenizer.reset();

            boolean hasnext = tokenizer.incrementToken();

            ArrayList<String> re = new ArrayList<>();
            while (hasnext) {
                CharTermAttribute ta = tokenizer.getAttribute(CharTermAttribute.class);
                OffsetAttribute offset = tokenizer.getAttribute(OffsetAttribute.class);
                System.out.printf("%s: %d -> %d\n", ta.toString(), offset.startOffset(), offset.endOffset());
                re.add(ta.toString());
                hasnext = tokenizer.incrementToken();
            }
            result.put(value, re);
        }
        return result;
    }

//    @Test
//    public void TestPinyinFunction() {
//        List<String> result = Pinyin.pinyin("貌美如誮");
//        for (int i = 0; i < result.size(); i++) {
//            String s = result.get(i);
//            System.out.println(s);
//        }
//    }


}
