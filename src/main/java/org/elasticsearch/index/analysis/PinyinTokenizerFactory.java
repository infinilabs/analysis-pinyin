package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.analysis.PinyinConfig;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;

import java.io.Reader;

public class PinyinTokenizerFactory extends AbstractTokenizerFactory {

    private PinyinConfig config;

    @Inject
    public PinyinTokenizerFactory(Index index, @IndexSettings Settings indexSettings, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        config=new PinyinConfig(settings);
    }

    @Override
    public Tokenizer create(Reader reader) {
            return new PinyinTokenizer(reader,config);
    }
}

