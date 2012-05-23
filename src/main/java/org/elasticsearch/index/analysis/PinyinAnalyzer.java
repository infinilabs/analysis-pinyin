package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by IntelliJ IDEA.
 * User: Medcl'
 * Date: 12-5-22
 * Time: 上午10:39
 */
public final class PinyinAnalyzer extends Analyzer {


    private String padding_char;
    private String only_first_letter;


    public PinyinAnalyzer(Settings settings) {
       only_first_letter = settings.get("only_first_letter", "false");
       padding_char = settings.get("padding_char", "");
    }

    @Override
    public TokenStream tokenStream(String fieldName, Reader reader) {
          if(only_first_letter.equals("false")){
            return new PinyinTokenizer(reader,padding_char);
        }else{
            return new PinyinAbbreviationsTokenizer(reader);
        }
    }
    @Override
    public TokenStream reusableTokenStream(String fieldName,Reader reader) throws IOException {

    //得到上一次使用的TokenStream，如果没有则生成新的，并且用setPreviousTokenStream放入成员变量，使得下一个可用。
    Tokenizer tokenizer = (Tokenizer) getPreviousTokenStream();

    if (tokenizer == null) {
      tokenizer = new PinyinTokenizer(reader,padding_char);
      setPreviousTokenStream(tokenizer);
    } else
    {
        //如果上一次生成过TokenStream，则reset初始化。
        tokenizer.reset(reader);
    }
    return tokenizer;
    }
}
