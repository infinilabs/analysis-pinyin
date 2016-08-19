package org.elasticsearch.index.analysis;

import org.elasticsearch.analysis.PinyinConfig;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

/**
 */
public class PinyinAnalyzerProvider extends AbstractIndexAnalyzerProvider<PinyinAnalyzer> {

    private final PinyinAnalyzer analyzer;
    private PinyinConfig config;

    @Inject
    public PinyinAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
        config=new PinyinConfig(settings);
        analyzer = new PinyinAnalyzer(config);
    }

    @Override
    public PinyinAnalyzer get() {
        return this.analyzer;
    }
}
