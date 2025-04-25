package com.infinilabs.elasticsearch.analysis;


import com.infinilabs.pinyin.analysis.PinyinConfig;
import com.infinilabs.pinyin.analysis.PinyinTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

public class PinyinTokenFilterFactory extends AbstractTokenFilterFactory {
    private PinyinConfig config;


    public PinyinTokenFilterFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(name);
       config=new ESPinyinConfig(settings);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new PinyinTokenFilter(tokenStream, config);
    }
}
