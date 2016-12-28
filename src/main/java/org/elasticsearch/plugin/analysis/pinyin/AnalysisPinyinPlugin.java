package org.elasticsearch.plugin.analysis.pinyin;


import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.PinyinAnalysisBinderProcessor;
import org.elasticsearch.plugins.AbstractPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class AnalysisPinyinPlugin extends AbstractPlugin {

    @Override
    public String name() {
        return "analysis-pinyin";
    }

    @Override
    public String description() {
        return "Chinese to Pinyin convert support";
    }

    public void onModule(AnalysisModule module) {
        module.addProcessor(new PinyinAnalysisBinderProcessor());
    }}
