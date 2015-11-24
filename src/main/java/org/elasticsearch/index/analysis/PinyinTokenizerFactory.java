package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;

public class PinyinTokenizerFactory extends AbstractTokenizerFactory {

    private String first_letter;
    private String padding_char;

    @Inject
    public PinyinTokenizerFactory(Index index, @IndexSettings Settings indexSettings, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        first_letter = settings.get("first_letter", "none");
        padding_char = settings.get("padding_char", "");
    }

    @Override
    public Tokenizer create() {
        if (first_letter.equals("only")) {
            return new PinyinAbbreviationsTokenizer();
        } else {
            return new PinyinTokenizer(first_letter, padding_char);
        }
    }
}

