package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.analysis.PinyinConfig;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

public class PinyinAbbreviationsTokenizerFactory extends AbstractTokenizerFactory {

    public PinyinAbbreviationsTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
    }

    @Override
    public Tokenizer create() {
        PinyinConfig config=new PinyinConfig();
        config.keepFirstLetter=true;
        config.keepNoneChinese=false;
        config.keepOriginal=false;
        config.keepFullPinyin=false;
        config.keepNoneChineseInFirstLetter=true;
        return new PinyinTokenizer(config);
    }
}
