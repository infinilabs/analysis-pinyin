package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.analysis.PinyinConfig;
import org.elasticsearch.common.settings.Settings;

import java.io.Reader;

/**
 * Created by IntelliJ IDEA.
 * User: Medcl'
 * Date: 12-5-22
 * Time: 上午10:39
 */
public final class PinyinAnalyzer extends Analyzer {

    private PinyinConfig config;

    public PinyinAnalyzer(PinyinConfig config) {
        this.config=config;
    }

    @Override
    protected TokenStreamComponents createComponents(String s, Reader reader) {
        return new TokenStreamComponents(new PinyinTokenizer(reader,config));

    }
}
