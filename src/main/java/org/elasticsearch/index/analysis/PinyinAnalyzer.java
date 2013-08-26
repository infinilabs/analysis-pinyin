package org.elasticsearch.index.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.common.settings.Settings;

/**
 * Created by IntelliJ IDEA. User: Medcl' Date: 12-5-22 Time: 上午10:39
 */
public final class PinyinAnalyzer extends Analyzer {

    private String mode;

    public PinyinAnalyzer(Settings settings) {
        mode = settings.get("mode", "none");
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        if (mode.equals("only")) {
            return new TokenStreamComponents(new PinyinAbbreviationsTokenizer(reader));
        } else {
            return new TokenStreamComponents(new PinyinTokenizer(reader, mode));
        }
    }

}
