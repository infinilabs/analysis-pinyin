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

import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.inject.ModulesBuilder;
import org.elasticsearch.common.settings.SettingsModule;
import org.elasticsearch.env.Environment;
import org.elasticsearch.env.EnvironmentModule;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.IndexNameModule;
import org.elasticsearch.index.settings.IndexSettingsModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.elasticsearch.common.settings.ImmutableSettings.Builder.EMPTY_SETTINGS;
import static org.hamcrest.Matchers.instanceOf;

/**
 */
public class PinyinAnalysisTests {

    @Test
    public void testPinyinAnalysis() {
        Index index = new Index("test");

        Injector parentInjector = new ModulesBuilder().add(new SettingsModule(EMPTY_SETTINGS), new EnvironmentModule(new Environment(EMPTY_SETTINGS)), new IndicesAnalysisModule()).createInjector();
        Injector injector = new ModulesBuilder().add(
                new IndexSettingsModule(index, EMPTY_SETTINGS),
                new IndexNameModule(index),
                new AnalysisModule(EMPTY_SETTINGS, parentInjector.getInstance(IndicesAnalysisService.class)).addProcessor(new PinyinAnalysisBinderProcessor()))
                .createChildInjector(parentInjector);

        AnalysisService analysisService = injector.getInstance(AnalysisService.class);

        TokenizerFactory tokenizerFactory = analysisService.tokenizer("pinyin");
        MatcherAssert.assertThat(tokenizerFactory, instanceOf(PinyinTokenizerFactory.class));

        TokenFilterFactory filterFactory = analysisService.tokenFilter("pinyin");
        MatcherAssert.assertThat(filterFactory, instanceOf(PinyinTokenFilterFactory.class));
    }

    @Test
    public void TestTokenizer() throws IOException {
        String[] s = {"刘德华", "劉德華","刘德华A1","刘德华A2"};
        for (String value : s) {
            System.out.println(value);
            StringReader sr = new StringReader(value);

            PinyinTokenizer tokenizer = new PinyinTokenizer(sr," ");
//            PinyinAbbreviationsTokenizer tokenizer = new PinyinAbbreviationsTokenizer(sr);

            boolean hasnext = tokenizer.incrementToken();

            while (hasnext) {

                TermAttribute ta = tokenizer.getAttribute(TermAttribute.class);

                System.out.println(ta.term());

                hasnext = tokenizer.incrementToken();

            }
        }

    }
}
