package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.common.settings.Settings;

/**
 * Created by IntelliJ IDEA.
 * User: Medcl'
 * Date: 12-5-22
 * Time: 上午10:39
 */
public final class PinyinAnalyzer extends Analyzer {


    private String padding_char;
    private String first_letter;


    public PinyinAnalyzer(Settings settings) {
        first_letter = settings.get("first_letter", "none");
        padding_char = settings.get("padding_char", "");
    }

    public PinyinAnalyzer(String firstLetter, String paddingChar) {
        first_letter = firstLetter;
        padding_char = paddingChar;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        if (first_letter.equals("only")) {
            return new TokenStreamComponents(new PinyinAbbreviationsTokenizer());
        } else {
            return new TokenStreamComponents(new PinyinTokenizer(first_letter, padding_char));
        }
    }

}
