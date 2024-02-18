package com.infinilabs.opensearch.analysis;

import com.infinilabs.pinyin.analysis.PinyinConfig;
import com.infinilabs.pinyin.analysis.PinyinTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.opensearch.common.settings.Settings;
import org.opensearch.env.Environment;
import org.opensearch.index.IndexSettings;
import org.opensearch.index.analysis.AbstractTokenizerFactory;

public class PinyinTokenizerFactory extends AbstractTokenizerFactory {

    private PinyinConfig config;

    public PinyinTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, settings, name);
        config = new ESPinyinConfig(settings);
    }

    @Override
    public Tokenizer create() {
        return new PinyinTokenizer(config);
    }
}

