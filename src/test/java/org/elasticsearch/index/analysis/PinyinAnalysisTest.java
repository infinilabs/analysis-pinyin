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
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
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

public class PinyinAnalysisTest {


    @Test
    public void testTokenFilter() throws IOException {
        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepOriginal = false;
        config.keepFullPinyin = false;
        config.ignorePinyinOffset = false;


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
        config.ignorePinyinOffset = false;


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
        config.ignorePinyinOffset = false;


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
        config.ignorePinyinOffset = false;


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
        Assert.assertEquals("刘德华", pinyin.get(1));
        Assert.assertEquals("ldh", pinyin.get(2));
        Assert.assertEquals("de", pinyin.get(3));
        Assert.assertEquals("hua", pinyin.get(4));



        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepNoneChinese = false;
        config.keepNoneChineseInFirstLetter = true;
        config.keepOriginal = false;
        config.keepFullPinyin = false;
        config.LimitFirstLetterLength = 5;
        config.lowercase = true;
        config.ignorePinyinOffset = false;


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


        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepSeparateFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepNoneChineseInFirstLetter = false;
        config.keepOriginal = false;
        config.keepFullPinyin = true;
        config.LimitFirstLetterLength = 5;
        config.lowercase = true;
        config.ignorePinyinOffset = false;


        sr = new StringReader("liu德hua 名字");
        analyzer = new WhitespaceAnalyzer();
        filter = new PinyinTokenFilter(analyzer.tokenStream("f", sr), config);
        filter.reset();
        System.out.println();
        pinyin = getTokenFilterResult(filter);

        Assert.assertEquals(9, pinyin.size());
        Assert.assertEquals("liu", pinyin.get(0));
        Assert.assertEquals("d", pinyin.get(1));
        Assert.assertEquals("de", pinyin.get(2));
        Assert.assertEquals("hua", pinyin.get(3));
        Assert.assertEquals("m", pinyin.get(4));
        Assert.assertEquals("ming", pinyin.get(5));
        Assert.assertEquals("z", pinyin.get(6));
        Assert.assertEquals("zi", pinyin.get(7));
        Assert.assertEquals("mz", pinyin.get(8));


        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepSeparateFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepNoneChineseInFirstLetter = false;
        config.keepOriginal = false;
        config.keepFullPinyin = true;
        config.LimitFirstLetterLength = 5;
        config.lowercase = true;
        config.noneChinesePinyinTokenize=true;
        config.removeDuplicateTerm=false;
        config.ignorePinyinOffset = false;


        sr = new StringReader("liudehuaalibaba13zhuanghan134");
        analyzer = new WhitespaceAnalyzer();
        filter = new PinyinTokenFilter(analyzer.tokenStream("f", sr), config);

        filter.reset();
        System.out.println();

        pinyin= getTokenFilterResult(filter);

        Assert.assertEquals(11, pinyin.size());
        Assert.assertEquals("liu", pinyin.get(0));
        Assert.assertEquals("de", pinyin.get(1));
        Assert.assertEquals("hua", pinyin.get(2));
        Assert.assertEquals("a", pinyin.get(3));
        Assert.assertEquals("li", pinyin.get(4));
        Assert.assertEquals("ba", pinyin.get(5));
        Assert.assertEquals("ba", pinyin.get(6));
        Assert.assertEquals("13", pinyin.get(7));
        Assert.assertEquals("zhuang", pinyin.get(8));
        Assert.assertEquals("han", pinyin.get(9));
        Assert.assertEquals("134", pinyin.get(10));



        config = new PinyinConfig();
        config.keepFirstLetter=true;
        config.keepFullPinyin=false;
        config.keepJoinedFullPinyin =true;
        config.keepNoneChinese=false;
        config.keepNoneChineseTogether=true;
        config.noneChinesePinyinTokenize=true;
        config.keepNoneChineseInFirstLetter=true;
        config.keepOriginal=false;
        config.lowercase=true;
        config.trimWhitespace=true;
        config.fixedPinyinOffset =true;
        config.ignorePinyinOffset = false;

        sr = new StringReader("刘德华");
        analyzer = new WhitespaceAnalyzer();
        filter = new PinyinTokenFilter(analyzer.tokenStream("f", sr), config);
        filter.reset();
        pinyin= getTokenFilterResult(filter);
        Assert.assertEquals("liudehua", pinyin.get(0));
        Assert.assertEquals("ldh", pinyin.get(1));


    }

    private List<String> getTokenFilterResult(PinyinTokenFilter filter)  throws IOException {
        List<String> pinyin = new ArrayList<String>();
        int pos=0;
        while (filter.incrementToken()) {
            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
            OffsetAttribute offset = filter.getAttribute(OffsetAttribute.class);
            PositionIncrementAttribute position = filter.getAttribute(PositionIncrementAttribute.class);
            pos=pos+position.getPositionIncrement();
            pinyin.add(ta.toString());
            Assert.assertTrue("startOffset must be non-negative",offset.startOffset()>=0);
            Assert.assertTrue("endOffset must be >= startOffset",offset.startOffset()>=0);
            System.out.println(ta.toString()+","+offset.startOffset()+","+offset.endOffset()+","+pos);
        }
        return pinyin;
    }


    @Test
    public void TestTokenizer() throws IOException {
        String[] s =
                {"刘德华"
                        , "劉德華", "刘德华A1",
                        "讲话频率小，不能发高音", "T波低平或倒置", "β-氨基酸尿",
                        "DJ音乐家", "人生一大乐事, 哈哈",
                };

        PinyinConfig config = new PinyinConfig();
        config.noneChinesePinyinTokenize=false;
        config.keepOriginal=true;
        config.ignorePinyinOffset = false;

        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re = result.get("刘德华");
        Assert.assertEquals(5, re.size());
        Assert.assertEquals("liu", re.get(0).term);
        Assert.assertEquals("刘德华", re.get(1).term);
        Assert.assertEquals("ldh", re.get(2).term);
        Assert.assertEquals("de", re.get(3).term);
        Assert.assertEquals("hua", re.get(4).term);


        re = result.get("劉德華");
        Assert.assertEquals(5, re.size());
        Assert.assertEquals("liu", re.get(0).term);
        Assert.assertEquals("劉德華", re.get(1).term);
        Assert.assertEquals("ldh", re.get(2).term);
        Assert.assertEquals("de", re.get(3).term);
        Assert.assertEquals("hua", re.get(4).term);


        re = result.get("刘德华A1");
        Assert.assertEquals(6, re.size());
        Assert.assertEquals("liu", re.get(0).term);
        Assert.assertEquals("刘德华a1", re.get(1).term);
        Assert.assertEquals("ldha1", re.get(2).term);
        Assert.assertEquals("de", re.get(3).term);
        Assert.assertEquals("hua", re.get(4).term);
        Assert.assertEquals("a1", re.get(5).term);


        re = result.get("讲话频率小，不能发高音");
        Assert.assertEquals(12, re.size());
        Assert.assertEquals("jiang", re.get(0).term);
        Assert.assertEquals("讲话频率小，不能发高音", re.get(1).term);
        Assert.assertEquals("jhplxbnfgy", re.get(2).term);
        Assert.assertEquals("hua", re.get(3).term);
        Assert.assertEquals("pin", re.get(4).term);
        Assert.assertEquals("lv", re.get(5).term);
        Assert.assertEquals("xiao", re.get(6).term);
        Assert.assertEquals("bu", re.get(7).term);
        Assert.assertEquals("neng", re.get(8).term);
        Assert.assertEquals("fa", re.get(9).term);
        Assert.assertEquals("gao", re.get(10).term);
        Assert.assertEquals("yin", re.get(11).term);


        re = result.get("T波低平或倒置");
        Assert.assertEquals(9, re.size());
        Assert.assertEquals("t", re.get(0).term);
        Assert.assertEquals("t波低平或倒置", re.get(1).term);
        Assert.assertEquals("tbdphdz", re.get(2).term);
        Assert.assertEquals("bo", re.get(3).term);
        Assert.assertEquals("di", re.get(4).term);
        Assert.assertEquals("ping", re.get(5).term);
        Assert.assertEquals("huo", re.get(6).term);
        Assert.assertEquals("dao", re.get(7).term);
        Assert.assertEquals("zhi", re.get(8).term);


        re = result.get("β-氨基酸尿");
        Assert.assertEquals(6, re.size());
        Assert.assertEquals("β-氨基酸尿", re.get(1).term);
        Assert.assertEquals("ajsn", re.get(2).term);
        Assert.assertEquals("an", re.get(0).term);
        Assert.assertEquals("ji", re.get(3).term);
        Assert.assertEquals("suan", re.get(4).term);
        Assert.assertEquals("niao", re.get(5).term);

        re = result.get("DJ音乐家");
        Assert.assertEquals(6, re.size());
        Assert.assertEquals("dj", re.get(0).term);
        Assert.assertEquals("dj音乐家", re.get(1).term);
        Assert.assertEquals("djyyj", re.get(2).term);
        Assert.assertEquals("yin", re.get(3).term);
        Assert.assertEquals("yue", re.get(4).term);
        Assert.assertEquals("jia", re.get(5).term);


        String[] s1 =
                {"刘德华", "刘 de 华"};
        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepSeparateFirstLetter = true;
        config.keepNoneChinese = false;
        config.keepNoneChineseInFirstLetter = false;
        config.keepOriginal = false;
        config.keepFullPinyin = true;
        config.LimitFirstLetterLength = 5;
        config.lowercase = false;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s1, config);

        re = result.get("刘德华");
        Assert.assertEquals(7, re.size());
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals("liu", re.get(1).term);
        Assert.assertEquals("ldh", re.get(2).term);
        Assert.assertEquals("d", re.get(3).term);
        Assert.assertEquals("de", re.get(4).term);
        Assert.assertEquals("h", re.get(5).term);
        Assert.assertEquals("hua", re.get(6).term);

        s1 = new String[]{"我的的"};
        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepSeparateFirstLetter = true;
        config.keepNoneChinese = false;
        config.keepNoneChineseInFirstLetter = false;
        config.keepOriginal = false;
        config.keepFullPinyin = true;
        config.LimitFirstLetterLength = 5;
        config.removeDuplicateTerm = true;
        config.lowercase = false;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s1, config);

        re = result.get("我的的");
        Assert.assertEquals(5, re.size());
        Assert.assertEquals("w", re.get(0).term);
        Assert.assertEquals("wo", re.get(1).term);
        Assert.assertEquals("wdd", re.get(2).term);
        Assert.assertEquals("d", re.get(3).term);
        Assert.assertEquals("de", re.get(4).term);

        s1 = new String[]{"lu金 s刘德华 张学友 郭富城 黎明 四大lao天王liudehua"};
        config = new PinyinConfig();
        config.keepFirstLetter=true;
        config.keepFullPinyin=false;
        config.keepNoneChinese=false;
        config.keepNoneChineseTogether=true;
        config.noneChinesePinyinTokenize=true;
        config.keepNoneChineseInFirstLetter=true;
        config.keepOriginal=false;
        config.lowercase=true;
        config.trimWhitespace=true;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s1, config);

        re = result.get("lu金 s刘德华 张学友 郭富城 黎明 四大lao天王liudehua");
        Assert.assertEquals("lujsldhzxygfclms", re.get(0).term);


        s1 = new String[]{"刘德华"};
        config = new PinyinConfig();
        config.keepFirstLetter=true;
        config.keepFullPinyin=false;
        config.keepJoinedFullPinyin =true;
        config.keepNoneChinese=false;
        config.keepNoneChineseTogether=true;
        config.noneChinesePinyinTokenize=true;
        config.keepNoneChineseInFirstLetter=true;
        config.keepOriginal=false;
        config.lowercase=true;
        config.trimWhitespace=true;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s1, config);

        re = result.get("刘德华");
        Assert.assertEquals("liudehua", re.get(0).term);
        Assert.assertEquals("ldh", re.get(1).term);

        s1 = new String[]{"刘德华"};
        config = new PinyinConfig();
        config.keepFirstLetter=false;
        config.keepFullPinyin=false;
        config.keepJoinedFullPinyin =true;
        config.keepNoneChinese=false;
        config.keepNoneChineseTogether=true;
        config.noneChinesePinyinTokenize=true;
        config.keepNoneChineseInFirstLetter=true;
        config.keepOriginal=false;
        config.lowercase=true;
        config.trimWhitespace=true;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s1, config);

        re = result.get("刘德华");
        Assert.assertEquals("liudehua", re.get(0).term);


        s1 = new String[]{"ceshi"};
        config = new PinyinConfig();
        config.keepFirstLetter=false;
        config.keepSeparateFirstLetter=false;
        config.keepFullPinyin=false;
        config.keepJoinedFullPinyin =true;
        config.keepNoneChinese=true;
        config.keepNoneChineseTogether=true;
        config.keepOriginal=true;
        config.LimitFirstLetterLength=16;
        config.noneChinesePinyinTokenize=true;
        config.lowercase=true;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s1, config);

        re = result.get("ceshi");
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals("shi", re.get(2).term);
        Assert.assertEquals("ceshi", re.get(1).term);




    }

    @Test
    public void TestFirstLetters() throws IOException {
        String[] s1 = new String[]{"刘德华"};
        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = false;
        config.keepSeparateFirstLetter = true;
        config.keepFullPinyin = false;
        config.keepJoinedFullPinyin = false;
        config.keepNoneChinese = true;
        config.keepNoneChineseTogether = true;
        config.keepOriginal = false;
        config.LimitFirstLetterLength = 16;
        config.noneChinesePinyinTokenize = true;
        config.lowercase = true;
        config.ignorePinyinOffset = false;


        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s1, config);

        ArrayList<TermItem> re = result.get("刘德华");
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals("d", re.get(1).term);
        Assert.assertEquals("h", re.get(2).term);

        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(2).startOffset);

        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals(2, re.get(1).endOffset);
        Assert.assertEquals(3, re.get(2).endOffset);
    }

    @Test
    public void TestOnlyLetters() throws IOException {
        String[] s1 = new String[]{"ldh"};
        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter=false;
        config.keepSeparateFirstLetter=false;
        config.keepFullPinyin=true;
        config.keepJoinedFullPinyin =false;
        config.keepNoneChinese=true;
        config.keepNoneChineseTogether=true;
        config.keepOriginal=false;
        config.LimitFirstLetterLength=16;
        config.noneChinesePinyinTokenize=true;
        config.lowercase=true;
        config.ignorePinyinOffset = false;


        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s1, config);

        ArrayList<TermItem> re = result.get("ldh");
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals("d", re.get(1).term);
        Assert.assertEquals("h", re.get(2).term);

        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(2).startOffset);

        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals(2, re.get(1).endOffset);
        Assert.assertEquals(3, re.get(2).endOffset);


        s1 = new String[]{"liuldhdehua"};
         config = new PinyinConfig();
        config.keepFirstLetter=false;
        config.keepSeparateFirstLetter=false;
        config.keepFullPinyin=true;
        config.keepJoinedFullPinyin =false;
        config.keepNoneChinese=true;
        config.keepNoneChineseTogether=true;
        config.keepOriginal=false;
        config.LimitFirstLetterLength=16;
        config.noneChinesePinyinTokenize=true;
        config.lowercase=true;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s1, config);

        re = result.get("liuldhdehua");
        Assert.assertEquals("liu", re.get(0).term);
        Assert.assertEquals("l", re.get(1).term);
        Assert.assertEquals("d", re.get(2).term);
        Assert.assertEquals("h", re.get(3).term);
        Assert.assertEquals("de", re.get(4).term);
        Assert.assertEquals("hua", re.get(5).term);

       s1 = new String[]{"liuldh"};
         config = new PinyinConfig();
        config.keepFirstLetter=false;
        config.keepSeparateFirstLetter=false;
        config.keepFullPinyin=true;
        config.keepJoinedFullPinyin =false;
        config.keepNoneChinese=true;
        config.keepNoneChineseTogether=true;
        config.keepOriginal=false;
        config.LimitFirstLetterLength=16;
        config.noneChinesePinyinTokenize=true;
        config.lowercase=true;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s1, config);

        re = result.get("liuldh");
        Assert.assertEquals("liu", re.get(0).term);
        Assert.assertEquals("l", re.get(1).term);
        Assert.assertEquals("d", re.get(2).term);
        Assert.assertEquals("h", re.get(3).term);

        s1 = new String[]{"ldhdehua"};
         config = new PinyinConfig();
        config.keepFirstLetter=false;
        config.keepSeparateFirstLetter=false;
        config.keepFullPinyin=true;
        config.keepJoinedFullPinyin =false;
        config.keepNoneChinese=true;
        config.keepNoneChineseTogether=true;
        config.keepOriginal=false;
        config.LimitFirstLetterLength=16;
        config.noneChinesePinyinTokenize=true;
        config.lowercase=true;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s1, config);

        re = result.get("ldhdehua");
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals("d", re.get(1).term);
        Assert.assertEquals("h", re.get(2).term);
        Assert.assertEquals("de", re.get(3).term);
        Assert.assertEquals("hua", re.get(4).term);

        s1 = new String[]{"ldh123dehua"};
         config = new PinyinConfig();
        config.keepFirstLetter=false;
        config.keepSeparateFirstLetter=false;
        config.keepFullPinyin=true;
        config.keepJoinedFullPinyin =false;
        config.keepNoneChinese=true;
        config.keepNoneChineseTogether=true;
        config.keepOriginal=false;
        config.LimitFirstLetterLength=16;
        config.noneChinesePinyinTokenize=true;
        config.lowercase=true;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s1, config);

        re = result.get("ldh123dehua");
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals("d", re.get(1).term);
        Assert.assertEquals("h", re.get(2).term);
        Assert.assertEquals("123", re.get(3).term);
        Assert.assertEquals("de", re.get(4).term);
        Assert.assertEquals("hua", re.get(5).term);
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
        config.ignorePinyinOffset = false;


        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re = result.get("刘德华");
        Assert.assertEquals(1, re.size());
        Assert.assertEquals("ldh", re.get(0).term);

        re = result.get("β-氨基酸尿");
        Assert.assertEquals(1, re.size());
        Assert.assertEquals("ajsn", re.get(0).term);

        re = result.get("DJ音乐家");
        Assert.assertEquals(3, re.size());
        Assert.assertEquals("d", re.get(0).term);
        Assert.assertEquals("djyyj", re.get(1).term);
        Assert.assertEquals("j", re.get(2).term);


        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepNoneChinese = false;
        config.keepNoneChineseInFirstLetter = false;
        config.keepOriginal = false;
        config.keepFullPinyin = false;
        config.keepNoneChineseTogether = false;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s, config);

        re = result.get("DJ音乐家");
        Assert.assertEquals(1, re.size());
        Assert.assertEquals("yyj", re.get(0).term);

        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepNoneChinese=true;
        config.keepNoneChineseInFirstLetter = true;
        config.keepNoneChineseTogether = true;
        config.keepOriginal = false;
        config.keepFullPinyin = false;
        config.noneChinesePinyinTokenize=false;
        config.ignorePinyinOffset = false;

        result = getStringArrayListHashMap(s, config);

        re = result.get("DJ音乐家");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("dj", re.get(0).term);
        Assert.assertEquals(1, re.get(0).position);
        Assert.assertEquals("djyyj", re.get(1).term);
        Assert.assertEquals(1, re.get(1).position);

    }

    @Test
    public void TestFullJoinedPinyin() throws IOException{
        String[] s =
                {"DJ音乐家"
                };
        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = false;
        config.keepNoneChineseInFirstLetter = false;
        config.keepOriginal = false;
        config.keepFullPinyin = false;
        config.noneChinesePinyinTokenize=false;
        config.keepNoneChinese=true;
        config.keepJoinedFullPinyin=true;
        config.keepNoneChineseTogether = true;
        config.keepNoneChineseInJoinedFullPinyin=true;
        config.ignorePinyinOffset = false;

        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re = result.get("DJ音乐家");
        Assert.assertEquals(1, re.size());
        Assert.assertEquals("djyinyuejia", re.get(0).term);
    }

    @Test
    public void TestMixedPinyinTokenizer() throws IOException {
        String[] s =
                {
                        "刘德华",
                        "刘de华",
                        "liude华",
                        " liude 华"};

        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepSeparateFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepOriginal = true;
        config.keepFullPinyin = true;
        config.keepNoneChineseTogether = true;
        config.ignorePinyinOffset = false;


        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re = result.get("刘德华");
        Assert.assertEquals(8, re.size());
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals("liu", re.get(1).term);
        Assert.assertEquals(0, re.get(1).startOffset);
        Assert.assertEquals(1, re.get(1).endOffset);

        Assert.assertEquals("刘德华", re.get(2).term);
        Assert.assertEquals(0, re.get(2).startOffset);
        Assert.assertEquals(3, re.get(2).endOffset);
        Assert.assertEquals("ldh", re.get(3).term);
        Assert.assertEquals(0, re.get(3).startOffset);
        Assert.assertEquals(3, re.get(3).endOffset);

        Assert.assertEquals("d", re.get(4).term);
        Assert.assertEquals(1, re.get(4).startOffset);
        Assert.assertEquals(2, re.get(4).endOffset);
        Assert.assertEquals("de", re.get(5).term);
        Assert.assertEquals(1, re.get(5).startOffset);
        Assert.assertEquals(2, re.get(5).endOffset);
        Assert.assertEquals("h", re.get(6).term);
        Assert.assertEquals(2, re.get(6).startOffset);
        Assert.assertEquals(3, re.get(6).endOffset);
        Assert.assertEquals("hua", re.get(7).term);
        Assert.assertEquals(2, re.get(7).startOffset);
        Assert.assertEquals(3, re.get(7).endOffset);

    }

    @Test
    public void TestMixedPinyinTokenizer2() throws IOException {
        String[] s =
                {
                        "ldh",
                        "ldhua",
                        "ld华",
                        "刘德华",
                        "刘de华",
                        "liude华",
                        " liude 华"};

        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = false;
        config.keepSeparateFirstLetter = false;
        config.keepNoneChinese = true;
        config.keepOriginal = true;
        config.keepFullPinyin = false;
        config.keepNoneChineseTogether = true;
        config.ignorePinyinOffset = false;
        config.keepSeparateChinese = true;


        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re = result.get("ldh");
        Assert.assertEquals(4, re.size());
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals("ldh", re.get(1).term);

        re = result.get("ldhua");
        Assert.assertEquals(4, re.size());
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals("hua", re.get(3).term);

        re = result.get("ld华");
        Assert.assertEquals(4, re.size());
        Assert.assertEquals("华", re.get(3).term);

    }

    @Test
    public void TestPinyinTokenizerOffsetWithExtraTerms() throws IOException {
        String[] s =
                {
                        "ceshi",
                        "测shi",
                        "ce试",
                        "测试",
                        "1测shi",
                };

        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = false;
        config.keepSeparateFirstLetter = false;
        config.keepNoneChinese = true;
        config.keepOriginal = false;
        config.keepFullPinyin = true;
        config.keepNoneChineseTogether = true;
        config.removeDuplicateTerm = true;
        config.fixedPinyinOffset=false;
        config.keepJoinedFullPinyin=false;
        config.ignorePinyinOffset = false;



        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re;

        re = result.get("ceshi");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(2, re.get(0).endOffset);
        Assert.assertEquals("shi", re.get(1).term);
        Assert.assertEquals(2, re.get(1).startOffset);
        Assert.assertEquals(5, re.get(1).endOffset);

        re = result.get("测shi");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals("shi", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(4, re.get(1).endOffset);

        re = result.get("ce试");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(2, re.get(0).endOffset);
        Assert.assertEquals("shi", re.get(1).term);
        Assert.assertEquals(2, re.get(1).startOffset);
        Assert.assertEquals(3, re.get(1).endOffset);

        re = result.get("测试");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals("shi", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(1).endOffset);

        re = result.get("1测shi");
        Assert.assertEquals(3, re.size());
        Assert.assertEquals("1", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals("ce", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(1).endOffset);
        Assert.assertEquals("shi", re.get(2).term);
        Assert.assertEquals(2, re.get(2).startOffset);
        Assert.assertEquals(5, re.get(2).endOffset);

    }

    @Test
    public void TestPinyinTokenizerOffset() throws IOException {
        String[] s =
                {
                        "ceshi",
                        "测shi",
                        "ce试",
                        "测试",
                        "1测shi",
                };

        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = false;
        config.keepSeparateFirstLetter = false;
        config.keepNoneChinese = true;
        config.keepOriginal = false;
        config.keepFullPinyin = true;
        config.keepNoneChineseTogether = true;
        config.fixedPinyinOffset=false;
        config.ignorePinyinOffset = false;

        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re;

        re = result.get("ceshi");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(2, re.get(0).endOffset);
        Assert.assertEquals("shi", re.get(1).term);
        Assert.assertEquals(2, re.get(1).startOffset);
        Assert.assertEquals(5, re.get(1).endOffset);

        re = result.get("测shi");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals("shi", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(4, re.get(1).endOffset);

        re = result.get("ce试");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(2, re.get(0).endOffset);
        Assert.assertEquals("shi", re.get(1).term);
        Assert.assertEquals(2, re.get(1).startOffset);
        Assert.assertEquals(3, re.get(1).endOffset);

        re = result.get("测试");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals("shi", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(1).endOffset);

        re = result.get("1测shi");
        Assert.assertEquals(3, re.size());
        Assert.assertEquals("1", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals("ce", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(1).endOffset);
        Assert.assertEquals("shi", re.get(2).term);
        Assert.assertEquals(2, re.get(2).startOffset);
        Assert.assertEquals(5, re.get(2).endOffset);

    }

    @Test
    public void TestPinyinTokenizerFixedOffset() throws IOException {
        String[] s =
                {
                        "ceshi",
                        "测shi",
//                        "ce试",
                        "测试",
                        "1测shi",
                };

        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = false;
        config.keepSeparateFirstLetter = false;
        config.keepNoneChinese = true;
        config.keepOriginal = false;
        config.keepFullPinyin = true;
        config.keepNoneChineseTogether = true;
        config.fixedPinyinOffset=true;
        config.ignorePinyinOffset = false;


        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re;

        re = result.get("ceshi");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals("shi", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(1).endOffset);

        re = result.get("测shi");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals("shi", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(1).endOffset);

//        re = result.get("ce试");
//        Assert.assertEquals(2, re.size());
//        Assert.assertEquals("ce", re.get(0).term);
//        Assert.assertEquals(0, re.get(0).startOffset);
//        Assert.assertEquals(1, re.get(0).endOffset);
//        Assert.assertEquals("shi", re.get(1).term);
//        Assert.assertEquals(1, re.get(1).startOffset);
//        Assert.assertEquals(2, re.get(1).endOffset);

        re = result.get("测试");
        Assert.assertEquals(2, re.size());
        Assert.assertEquals("ce", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals("shi", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(1).endOffset);

        re = result.get("1测shi");
        Assert.assertEquals(3, re.size());
        Assert.assertEquals("1", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals("ce", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(1).endOffset);
        Assert.assertEquals("shi", re.get(2).term);
        Assert.assertEquals(2, re.get(2).startOffset);
        Assert.assertEquals(3, re.get(2).endOffset);

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

    private HashMap<String, ArrayList<TermItem>> getStringArrayListHashMap(String[] s, PinyinConfig config) throws IOException {
        HashMap<String, ArrayList<TermItem>> result = new HashMap<>();
        for (String value : s) {
            System.out.println("\n" + value);
            StringReader sr = new StringReader(value);

            PinyinTokenizer tokenizer = new PinyinTokenizer(config);
            tokenizer.setReader(sr);

            tokenizer.reset();

            boolean hasnext = tokenizer.incrementToken();

            int pos=0;
            ArrayList<TermItem> re = new ArrayList<>();
            while (hasnext) {
                CharTermAttribute ta = tokenizer.getAttribute(CharTermAttribute.class);
                PositionIncrementAttribute position = tokenizer.getAttribute(PositionIncrementAttribute.class);
                OffsetAttribute offset = tokenizer.getAttribute(OffsetAttribute.class);
                pos=pos+position.getPositionIncrement();
                System.out.printf("%s: %d -> %d ,%d\n", ta.toString(), offset.startOffset(), offset.endOffset(),pos);
                re.add(new TermItem(ta.toString(),offset.startOffset(),offset.endOffset(),pos));
                hasnext = tokenizer.incrementToken();
            }
            result.put(value, re);
        }
        return result;
    }

    @Test
    public void TestPinyinFunction() {
        List<String> result = Pinyin.pinyin("貌美如誮");
        for (int i = 0; i < result.size(); i++) {
            String s = result.get(i);
            System.out.println(s);
        }
        Assert.assertEquals("mao", result.get(0));
        Assert.assertEquals("mei", result.get(1));
        Assert.assertEquals("ru", result.get(2));
        Assert.assertEquals("hua", result.get(3));
    }

    @Test
    public void TestPinyinTokenize(){
        String str ="liudehuaalibaba13zhuanghan134";
        List<String> result = PinyinAlphabetTokenizer.walk(str);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        Assert.assertEquals("liu", result.get(0));
        Assert.assertEquals("de", result.get(1));
        Assert.assertEquals("hua", result.get(2));
        Assert.assertEquals("a", result.get(3));
        Assert.assertEquals("li", result.get(4));
        Assert.assertEquals("ba", result.get(5));
        Assert.assertEquals("ba", result.get(6));
        Assert.assertEquals("13", result.get(7));
        Assert.assertEquals("zhuang", result.get(8));
        Assert.assertEquals("han", result.get(9));
        Assert.assertEquals("134", result.get(10));

        str ="a123";
        result = PinyinAlphabetTokenizer.walk(str);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        Assert.assertEquals("a", result.get(0));
        Assert.assertEquals("123", result.get(1));

        str ="liudehua";
        result = PinyinAlphabetTokenizer.walk(str);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        Assert.assertEquals("liu", result.get(0));
        Assert.assertEquals("de", result.get(1));
        Assert.assertEquals("hua", result.get(2));


        str ="ceshi";
        result = PinyinAlphabetTokenizer.walk(str);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(i+": "+result.get(i));
        }
        Assert.assertEquals("ce", result.get(0));
        Assert.assertEquals("shi", result.get(1));
    }

    @Test
    public void TestPinyinPosition1() throws IOException {
        String[] s ={ "刘德华"};

        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepSeparateFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepOriginal = true;
        config.keepFullPinyin = true;
        config.keepNoneChineseTogether = true;
        config.ignorePinyinOffset = false;

        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re = result.get("刘德华");
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals(1, re.get(0).position);
        Assert.assertEquals("liu", re.get(1).term);
        Assert.assertEquals(0, re.get(1).startOffset);
        Assert.assertEquals(1, re.get(1).endOffset);
        Assert.assertEquals(1, re.get(1).position);

        Assert.assertEquals("刘德华", re.get(2).term);
        Assert.assertEquals(0, re.get(2).startOffset);
        Assert.assertEquals(3, re.get(2).endOffset);
        Assert.assertEquals(1, re.get(2).position);
        Assert.assertEquals("ldh", re.get(3).term);
        Assert.assertEquals(0, re.get(3).startOffset);
        Assert.assertEquals(3, re.get(3).endOffset);
        Assert.assertEquals(1, re.get(3).position);

        Assert.assertEquals("d", re.get(4).term);
        Assert.assertEquals(1, re.get(4).startOffset);
        Assert.assertEquals(2, re.get(4).endOffset);
        Assert.assertEquals(2, re.get(4).position);
        Assert.assertEquals("de", re.get(5).term);
        Assert.assertEquals(1, re.get(5).startOffset);
        Assert.assertEquals(2, re.get(5).endOffset);
        Assert.assertEquals(2, re.get(5).position);
        Assert.assertEquals("h", re.get(6).term);
        Assert.assertEquals(2, re.get(6).startOffset);
        Assert.assertEquals(3, re.get(6).endOffset);
        Assert.assertEquals(3, re.get(6).position);
        Assert.assertEquals("hua", re.get(7).term);
        Assert.assertEquals(2, re.get(7).startOffset);
        Assert.assertEquals(3, re.get(7).endOffset);
        Assert.assertEquals(3, re.get(7).position);
    }

    @Test
    public void TestPinyinPosition2() throws IOException {
        String[] s ={ "l德华"};

        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepSeparateFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepOriginal = true;
        config.keepFullPinyin = true;
        config.keepNoneChineseTogether = true;
        config.ignorePinyinOffset = false;


        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re = result.get("l德华");
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals(1, re.get(0).position);

        Assert.assertEquals("l德华", re.get(1).term);
        Assert.assertEquals(0, re.get(1).startOffset);
        Assert.assertEquals(3, re.get(1).endOffset);
        Assert.assertEquals(1, re.get(1).position);
        Assert.assertEquals("ldh", re.get(2).term);
        Assert.assertEquals(0, re.get(2).startOffset);
        Assert.assertEquals(3, re.get(2).endOffset);
        Assert.assertEquals(1, re.get(2).position);

        Assert.assertEquals("d", re.get(3).term);
        Assert.assertEquals(1, re.get(3).startOffset);
        Assert.assertEquals(2, re.get(3).endOffset);
        Assert.assertEquals(2, re.get(3).position);
        Assert.assertEquals("de", re.get(4).term);
        Assert.assertEquals(1, re.get(4).startOffset);
        Assert.assertEquals(2, re.get(4).endOffset);
        Assert.assertEquals(2, re.get(4).position);
        Assert.assertEquals("h", re.get(5).term);
        Assert.assertEquals(2, re.get(5).startOffset);
        Assert.assertEquals(3, re.get(5).endOffset);
        Assert.assertEquals(3, re.get(5).position);
        Assert.assertEquals("hua", re.get(6).term);
        Assert.assertEquals(2, re.get(6).startOffset);
        Assert.assertEquals(3, re.get(6).endOffset);
        Assert.assertEquals(3, re.get(6).position);



    }

    @Test
    public void TestPinyinPositionWithNonChinese() throws IOException {
        String[] s ={
                "l德华",
                "liu德华"
        };

        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = false;
        config.keepSeparateFirstLetter = true;

        config.keepNoneChinese = true;
        config.keepNoneChineseTogether=false;

        config.keepNoneChineseInFirstLetter=true;
        config.keepOriginal = false;
        config.keepFullPinyin = false;
        config.ignorePinyinOffset = false;


        HashMap<String, ArrayList<TermItem>>  result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re = result.get("l德华");
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals(1, re.get(0).position);

        Assert.assertEquals("d", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(1).endOffset);
        Assert.assertEquals(2, re.get(1).position);

        Assert.assertEquals("h", re.get(2).term);
        Assert.assertEquals(2, re.get(2).startOffset);
        Assert.assertEquals(3, re.get(2).endOffset);
        Assert.assertEquals(3, re.get(2).position);


        config = new PinyinConfig();
        config.keepFirstLetter = false;
        config.keepSeparateFirstLetter = true;

        config.keepNoneChinese = true;
        config.keepNoneChineseTogether=true;

        config.keepNoneChineseInFirstLetter=true;
        config.keepOriginal = false;
        config.keepFullPinyin = false;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s, config);

        re = result.get("l德华");
        Assert.assertEquals("l", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals(1, re.get(0).position);

        Assert.assertEquals("d", re.get(1).term);
        Assert.assertEquals(1, re.get(1).startOffset);
        Assert.assertEquals(2, re.get(1).endOffset);
        Assert.assertEquals(2, re.get(1).position);

        Assert.assertEquals("h", re.get(2).term);
        Assert.assertEquals(2, re.get(2).startOffset);
        Assert.assertEquals(3, re.get(2).endOffset);
        Assert.assertEquals(3, re.get(2).position);

        re = result.get("liu德华");
        Assert.assertEquals("liu", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(3, re.get(0).endOffset);
        Assert.assertEquals(1, re.get(0).position);

        Assert.assertEquals("d", re.get(1).term);
        Assert.assertEquals(3, re.get(1).startOffset);
        Assert.assertEquals(4, re.get(1).endOffset);
        Assert.assertEquals(2, re.get(1).position);

        Assert.assertEquals("h", re.get(2).term);
        Assert.assertEquals(4, re.get(2).startOffset);
        Assert.assertEquals(5, re.get(2).endOffset);
        Assert.assertEquals(3, re.get(2).position);

    }


    @Test
    public void TestPinyinPosition3() throws IOException {
        String[] s ={ "liude华","liudehua","ldhua","刘de华","刘dehua","DJ音乐家"};

        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepSeparateFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepOriginal = true;
        config.keepFullPinyin = true;
        config.keepNoneChineseTogether = true;
        config.ignorePinyinOffset = false;


        HashMap<String, ArrayList<TermItem>> result = getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re = result.get("liude华");
        Assert.assertEquals("liu", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(3, re.get(0).endOffset);
        Assert.assertEquals(1, re.get(0).position);

        Assert.assertEquals("liude华", re.get(1).term);
        Assert.assertEquals(0, re.get(1).startOffset);
        Assert.assertEquals(6, re.get(1).endOffset);
        Assert.assertEquals(1, re.get(1).position);

        Assert.assertEquals("liudeh", re.get(2).term);
        Assert.assertEquals(0, re.get(2).startOffset);
        Assert.assertEquals(6, re.get(2).endOffset);
        Assert.assertEquals(1, re.get(2).position);

        Assert.assertEquals("de", re.get(3).term);
        Assert.assertEquals(3, re.get(3).startOffset);
        Assert.assertEquals(5, re.get(3).endOffset);
        Assert.assertEquals(2, re.get(3).position);


        Assert.assertEquals("h", re.get(4).term);
        Assert.assertEquals(5, re.get(4).startOffset);
        Assert.assertEquals(6, re.get(4).endOffset);
        Assert.assertEquals(3, re.get(4).position);

        Assert.assertEquals("hua", re.get(5).term);
        Assert.assertEquals(5, re.get(5).startOffset);
        Assert.assertEquals(6, re.get(5).endOffset);
        Assert.assertEquals(3, re.get(5).position);

    }

    @Test
    public void TestPinyinPosition4() throws IOException {
        String[] s ={ "medcl"};

        PinyinConfig config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepSeparateFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepOriginal = true;
        config.keepFullPinyin = true;
        config.keepNoneChineseTogether = true;
        config.ignorePinyinOffset = false;


        HashMap<String, ArrayList<TermItem>> result= getStringArrayListHashMap(s, config);

        ArrayList<TermItem> re = result.get("medcl");
        Assert.assertEquals("me", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(2, re.get(0).endOffset);
        Assert.assertEquals(1, re.get(0).position);

        Assert.assertEquals("medcl", re.get(1).term);
        Assert.assertEquals(0, re.get(1).startOffset);
        Assert.assertEquals(5, re.get(1).endOffset);
        Assert.assertEquals(1, re.get(1).position);

        config = new PinyinConfig();
        config.keepFirstLetter = true;
        config.keepSeparateFirstLetter = true;
        config.keepNoneChinese = true;
        config.keepOriginal = true;
        config.keepFullPinyin = true;
        config.keepNoneChineseTogether = false;
        config.keepJoinedFullPinyin = true;
        config.ignorePinyinOffset = false;


        result = getStringArrayListHashMap(s, config);

        re = result.get("medcl");
        Assert.assertEquals("m", re.get(0).term);
        Assert.assertEquals(0, re.get(0).startOffset);
        Assert.assertEquals(1, re.get(0).endOffset);
        Assert.assertEquals(1, re.get(0).position);

        Assert.assertEquals("medcl", re.get(1).term);
        Assert.assertEquals(0, re.get(1).startOffset);
        Assert.assertEquals(5, re.get(1).endOffset);
        Assert.assertEquals(1, re.get(1).position);



        Assert.assertEquals("e", re.get(2).term);
        Assert.assertEquals(1, re.get(2).startOffset);
        Assert.assertEquals(2, re.get(2).endOffset);
        Assert.assertEquals(2, re.get(2).position);

        Assert.assertEquals("d", re.get(3).term);
        Assert.assertEquals(2, re.get(3).startOffset);
        Assert.assertEquals(3, re.get(3).endOffset);
        Assert.assertEquals(3, re.get(3).position);


    }
}
