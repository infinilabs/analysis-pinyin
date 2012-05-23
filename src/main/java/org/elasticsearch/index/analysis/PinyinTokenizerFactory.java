package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettings;

import java.io.Reader;

/**
 */
public class PinyinTokenizerFactory extends AbstractTokenizerFactory {

   private String only_first_letter;
   private String padding_char;
    @Inject
    public PinyinTokenizerFactory(Index index, @IndexSettings Settings indexSettings, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
         only_first_letter = settings.get("only_first_letter", "false");
         padding_char = settings.get("padding_char", "");
    }

    @Override
    public Tokenizer create(Reader reader) {
        if(only_first_letter.equals("false")){
            return new PinyinTokenizer(reader,padding_char);
        }else{
            return new PinyinAbbreviationsTokenizer(reader);
        }
    }
}
