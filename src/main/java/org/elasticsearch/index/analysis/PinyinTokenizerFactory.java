package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.analysis.PinyinConfig;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

public class PinyinTokenizerFactory extends AbstractTokenizerFactory {

    private PinyinConfig config;

    public PinyinTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, settings, name);
        config=new PinyinConfig(settings);
    }

    @Override
    public Tokenizer create() {
            return new PinyinTokenizer(config);
    }
}

