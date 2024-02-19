package com.infinilabs.opensearch.analysis;

import com.infinilabs.pinyin.analysis.PinyinAnalyzer;
import com.infinilabs.pinyin.analysis.PinyinConfig;
import org.opensearch.common.inject.Inject;
import org.opensearch.common.settings.Settings;
import org.opensearch.env.Environment;
import org.opensearch.index.IndexSettings;
import org.opensearch.index.analysis.AbstractIndexAnalyzerProvider;

/**
 *
 */
public class PinyinAnalyzerProvider extends AbstractIndexAnalyzerProvider<PinyinAnalyzer> {

    private final PinyinAnalyzer analyzer;
    private PinyinConfig config;

    @Inject
    public PinyinAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
        config = new ESPinyinConfig(settings);
        analyzer = new PinyinAnalyzer(config);
    }

    @Override
    public PinyinAnalyzer get() {
        return this.analyzer;
    }
}
