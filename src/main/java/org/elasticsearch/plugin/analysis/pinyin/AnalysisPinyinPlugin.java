package org.elasticsearch.plugin.analysis.pinyin;

import org.elasticsearch.analysis.PinyinIndicesAnalysisModule;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.PinyinAnalysisBinderProcessor;
import org.elasticsearch.plugins.Plugin;

import java.util.Collection;
import java.util.Collections;

/**
 * The Pinyin Analysis plugin integrates Pinyin4j(http://pinyin4j.sourceforge.net/) module into elasticsearch.
 */
public class AnalysisPinyinPlugin extends Plugin {

    @Override
    public String name() {
        return "analysis-pinyin";
    }

    @Override
    public String description() {
        return "convert Chinese to Pinyin";
    }


    @Override
    public Collection<Module> nodeModules() {
        return Collections.<Module>singletonList(new PinyinIndicesAnalysisModule());
    }

    public void onModule(AnalysisModule module) {
        module.addProcessor(new PinyinAnalysisBinderProcessor());
    }
}
