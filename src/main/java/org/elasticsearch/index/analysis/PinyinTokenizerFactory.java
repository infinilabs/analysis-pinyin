package org.elasticsearch.index.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;

/**
 */
public class PinyinTokenizerFactory extends AbstractTokenizerFactory {

    private String mode;

    @Inject
    public PinyinTokenizerFactory(Index index, @IndexSettings Settings indexSettings,
            @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        mode = settings.get("mode", "none");
    }

    @Override
    public Tokenizer create(Reader reader) {
        if (mode.equals("only")) {
            return new PinyinAbbreviationsTokenizer(reader);
        } else {
            return new PinyinTokenizer(reader, mode);
        }
    }
}
