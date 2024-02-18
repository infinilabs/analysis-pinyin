package com.infinilabs.elasticsearch.analysis;

import com.infinilabs.pinyin.analysis.PinyinConfig;
import com.infinilabs.pinyin.analysis.PinyinTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

public class PinyinTokenizerFactory extends AbstractTokenizerFactory {

    private PinyinConfig config;

    public PinyinTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, settings, name);
        config=new ESPinyinConfig(settings);
    }

    @Override
    public Tokenizer create() {
            return new PinyinTokenizer(config);
    }
}

