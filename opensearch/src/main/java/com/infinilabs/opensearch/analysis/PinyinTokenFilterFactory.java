package com.infinilabs.opensearch.analysis;


import com.infinilabs.pinyin.analysis.PinyinConfig;
import com.infinilabs.pinyin.analysis.PinyinTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.opensearch.common.settings.Settings;
import org.opensearch.env.Environment;
import org.opensearch.index.IndexSettings;
import org.opensearch.index.analysis.AbstractTokenFilterFactory;

public class PinyinTokenFilterFactory extends AbstractTokenFilterFactory {
    private PinyinConfig config;


    public PinyinTokenFilterFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(indexSettings, name, settings);
        config = new ESPinyinConfig(settings);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new PinyinTokenFilter(tokenStream, config);
    }
}
