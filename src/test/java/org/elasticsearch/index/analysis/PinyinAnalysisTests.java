///*
//* Licensed to ElasticSearch and Shay Banon under one
//* or more contributor license agreements.  See the NOTICE file
//* distributed with this work for additional information
//* regarding copyright ownership. ElasticSearch licenses this
//* file to you under the Apache License, Version 2.0 (the
//* "License"); you may not use this file except in compliance
//* with the License.  You may obtain a copy of the License at
//*
//*    http://www.apache.org/licenses/LICENSE-2.0
//*
//* Unless required by applicable law or agreed to in writing,
//* software distributed under the License is distributed on an
//* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
//* KIND, either express or implied.  See the License for the
//* specific language governing permissions and limitations
//* under the License.
//*/
//
//package org.elasticsearch.index.analysis;
//
//import junit.framework.Assert;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.core.KeywordAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
//import org.apache.lucene.util.Version;
//import org.elasticsearch.common.inject.Injector;
//import org.elasticsearch.common.inject.ModulesBuilder;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.settings.SettingsModule;
//import org.elasticsearch.env.Environment;
//import org.elasticsearch.env.EnvironmentModule;
//import org.elasticsearch.index.Index;
//import org.elasticsearch.index.IndexNameModule;
//import org.elasticsearch.index.settings.IndexSettingsModule;
//import org.elasticsearch.indices.analysis.IndicesAnalysisService;
//import org.hamcrest.MatcherAssert;
//import org.testng.annotations.Test;
//
//import java.io.IOException;
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.instanceOf;
//
///**
//*/
//public class PinyinAnalysisTests {
//
//    @Test
//    public void testPinyinAnalysis() {
//        Index index = new Index("test");
//
//        Injector parentInjector = new ModulesBuilder().add(new SettingsModule(Settings.EMPTY), new EnvironmentModule(new Environment(Settings.EMPTY))).createInjector();
//        Injector injector = new ModulesBuilder().add(
//                new IndexSettingsModule(index, Settings.EMPTY),
//                new IndexNameModule(index),
//                new AnalysisModule(Settings.EMPTY, parentInjector.getInstance(IndicesAnalysisService.class)).addProcessor(new PinyinAnalysisBinderProcessor()))
//                .createChildInjector(parentInjector);
//
//        AnalysisService analysisService = injector.getInstance(AnalysisService.class);
//
//
//        TokenizerFactory tokenizerFactory = analysisService.tokenizer("pinyin");
//        MatcherAssert.assertThat(tokenizerFactory, instanceOf(PinyinTokenizerFactory.class));
//
//        TokenFilterFactory tokenFilterFactory = analysisService.tokenFilter("pinyin");
//        MatcherAssert.assertThat(tokenFilterFactory,instanceOf(PinyinTokenFilterFactory.class));
//
//
//    }
//
//    @Test
//    public void testTokenFilter() throws IOException{
//        StringReader sr = new StringReader("刘德华");
//        Analyzer analyzer = new StandardAnalyzer();
//        PinyinTokenFilter filter = new PinyinTokenFilter(analyzer.tokenStream("f",sr),"","none");
//        List<String>  pinyin= new ArrayList<String>();
//        filter.reset();
//        while (filter.incrementToken())
//        {
//            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
//            pinyin.add(ta.toString());
//        }
////        Assert.assertEquals(3,pinyin.size());
//        System.out.println(pinyin.get(0));
//        System.out.println(pinyin.get(1));
//        System.out.println(pinyin.get(2));
//        Assert.assertEquals("liu",pinyin.get(0));
//        Assert.assertEquals("de",pinyin.get(1));
//        Assert.assertEquals("hua",pinyin.get(2));
//
//        sr = new StringReader("刘德华");
//        analyzer = new KeywordAnalyzer();
//        filter = new PinyinTokenFilter(analyzer.tokenStream("f",sr),"","only");
//        pinyin.clear();
//        while (filter.incrementToken())
//        {
//            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
//            pinyin.add(ta.toString());
//        }
//        Assert.assertEquals(1,pinyin.size());
//        Assert.assertEquals("ldh",pinyin.get(0));
//    }
//
//    @Test
//    public void TestTokenizer() throws IOException {
//        String[] s = {"刘德华", "劉德華", "刘德华A1", "刘德华A2","讲话频率小，不能发高音","T波低平或倒置","β-氨基酸尿"};
//        for (String value : s) {
//            System.out.println(value);
//            StringReader sr = new StringReader(value);
//
//            PinyinTokenizer tokenizer = new PinyinTokenizer(" ","none");
//            tokenizer.setReader(sr);
////            PinyinTokenizer tokenizer = new PinyinTokenizer(sr, " ", "only");
////            PinyinTokenizer tokenizer = new PinyinTokenizer(sr," ","prefix");
////              PinyinTokenizer tokenizer = new PinyinTokenizer(sr," ","append");
////            PinyinAbbreviationsTokenizer tokenizer = new PinyinAbbreviationsTokenizer(sr);
//
//            boolean hasnext = tokenizer.incrementToken();
//
//            while (hasnext) {
//
//                CharTermAttribute ta = tokenizer.getAttribute(CharTermAttribute.class);
//
//                System.out.println(ta.toString());
//
//                hasnext = tokenizer.incrementToken();
//
//            }
//        }
//
//    }
//}
