package org.elasticsearch.index.analysis;


import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.analysis.PinyinConfig;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

public class PinyinTokenFilterFactory extends AbstractTokenFilterFactory {
    private PinyinConfig config;


    public PinyinTokenFilterFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(indexSettings, name, settings);
       config=new PinyinConfig(settings);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new PinyinTokenFilter(tokenStream, config);
    }
}
