package com.infinilabs.elasticsearch.analysis;

import com.infinilabs.pinyin.analysis.PinyinConfig;
import com.infinilabs.pinyin.analysis.PinyinTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

public class PinyinAbbreviationsTokenizerFactory extends AbstractTokenizerFactory {

    public PinyinAbbreviationsTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, settings, name);
    }

    @Override
    public Tokenizer create() {
        PinyinConfig config=new ESPinyinConfig();
        config.keepFirstLetter=true;
        config.keepFullPinyin=false;
        config.keepNoneChinese=false;
        config.keepNoneChineseTogether=true;
        config.noneChinesePinyinTokenize=false;
        config.keepOriginal=false;
        config.lowercase=true;
        config.trimWhitespace=true;
        config.keepNoneChineseInFirstLetter=true;
        return new PinyinTokenizer(config);
    }
}
