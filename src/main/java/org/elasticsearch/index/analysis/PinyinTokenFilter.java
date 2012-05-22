package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenStream;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Medcl'
 * Date: 12-5-22
 * Time: 上午10:39
 */
public class PinyinTokenFilter extends TokenStream {
    public PinyinTokenFilter(TokenStream tokenStream) {
    }

    @Override
    public boolean incrementToken() throws IOException {
        return false;
    }
}
