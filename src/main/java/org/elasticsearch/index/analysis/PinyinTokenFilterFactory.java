package org.elasticsearch.index.analysis;


import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;

public class PinyinTokenFilterFactory extends AbstractTokenFilterFactory {
    private String first_letter;
    private String padding_char;

    @Inject
    public PinyinTokenFilterFactory(Index index, IndexSettingsService indexSettings, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings.getSettings(), name, settings);
        first_letter = settings.get("first_letter", "none");
        padding_char = settings.get("padding_char", "");
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new PinyinTokenFilter(tokenStream, first_letter, padding_char);
    }
}