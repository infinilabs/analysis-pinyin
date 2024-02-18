package com.infinilabs.pinyin.analysis;

import org.apache.lucene.analysis.Analyzer;

/**
 * Created by IntelliJ IDEA.
 * User: Medcl'
 * Date: 12-5-22
 * Time: 上午10:39
 */
public final class PinyinAnalyzer extends Analyzer {

    private PinyinConfig config;

    public PinyinAnalyzer(PinyinConfig config) {
        this.config=config;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
            return new TokenStreamComponents(new PinyinTokenizer(config));
    }

}
