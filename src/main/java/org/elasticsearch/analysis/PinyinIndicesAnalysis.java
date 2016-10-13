package org.elasticsearch.analysis;


import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;

/**
 * Registers indices level analysis components so, if not explicitly configured,
 * will be shared among all indices.
 */
public class PinyinIndicesAnalysis extends AbstractComponent {

    @Inject
    public PinyinIndicesAnalysis(final Settings settings,
                                 IndicesAnalysisService indicesAnalysisService, Environment env) {
        super(settings);

        final PinyinConfig config = new PinyinConfig(settings);

        //analyzers
        indicesAnalysisService.analyzerProviderFactories().put("pinyin",
                new PreBuiltAnalyzerProviderFactory("pinyin", AnalyzerScope.GLOBAL,
                        new PinyinAnalyzer(config)));

        //tokenizers
        indicesAnalysisService.tokenizerFactories().put("pinyin",
                new PreBuiltTokenizerFactoryFactory(new TokenizerFactory() {
                    @Override
                    public String name() {
                        return "pinyin";
                    }

                    @Override
                    public Tokenizer create() {
                        return new PinyinTokenizer(config);
                    }
                }));



        //tokenFilters
        indicesAnalysisService.tokenFilterFactories().put("pinyin",
                new PreBuiltTokenFilterFactoryFactory(new TokenFilterFactory() {
                    @Override
                    public String name() {
                        return "pinyin";
                    }

                    @Override
                    public TokenStream create(TokenStream tokenStream) {
                        return new PinyinTokenFilter(tokenStream, config);
                    }
                }));


    }
}
