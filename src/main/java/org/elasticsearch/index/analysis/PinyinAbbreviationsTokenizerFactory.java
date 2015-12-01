package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;

public class PinyinAbbreviationsTokenizerFactory extends AbstractTokenizerFactory {
    @Inject
    public PinyinAbbreviationsTokenizerFactory(Index index, IndexSettingsService  indexSettings, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings.getSettings(), name, settings);
    }

    @Override
    public Tokenizer create() {
        return new PinyinAbbreviationsTokenizer();
    }
}
