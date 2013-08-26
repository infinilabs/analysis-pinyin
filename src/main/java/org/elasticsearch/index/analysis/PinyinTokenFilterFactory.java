package org.elasticsearch.index.analysis;


import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;


/**
 * @author kimchy (shay.banon)
 */
public class PinyinTokenFilterFactory extends AbstractTokenFilterFactory {
    private String mode;

    @Inject
    public PinyinTokenFilterFactory(Index index, @IndexSettings Settings indexSettings,
            @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        mode = settings.get("mode", "none");
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new PinyinTokenFilter(tokenStream, mode);
    }
}
