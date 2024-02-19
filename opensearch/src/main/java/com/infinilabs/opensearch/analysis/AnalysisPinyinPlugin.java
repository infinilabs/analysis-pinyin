package com.infinilabs.opensearch.analysis;


import org.apache.lucene.analysis.Analyzer;
import org.opensearch.index.analysis.*;
import org.opensearch.indices.analysis.AnalysisModule;
import org.opensearch.plugins.AnalysisPlugin;
import org.opensearch.plugins.Plugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class AnalysisPinyinPlugin extends Plugin implements AnalysisPlugin {

  @Override
  public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
    Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> extra = new HashMap<>();
    extra.put("pinyin", PinyinTokenizerFactory::new);
    extra.put("pinyin_first_letter", PinyinAbbreviationsTokenizerFactory::new);
    return extra;
  }

  @Override
  public Map<String, AnalysisModule.AnalysisProvider<org.opensearch.index.analysis.TokenFilterFactory>> getTokenFilters() {
    Map<String, AnalysisModule.AnalysisProvider<org.opensearch.index.analysis.TokenFilterFactory>> extra = new HashMap<>();
    extra.put("pinyin", PinyinTokenFilterFactory::new);
    return extra;
  }

  @Override
  public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
    return Collections.singletonMap("pinyin", PinyinAnalyzerProvider::new);
  }
}
