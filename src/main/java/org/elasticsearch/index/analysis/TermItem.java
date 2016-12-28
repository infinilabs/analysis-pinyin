package org.elasticsearch.index.analysis;

/**
 * Created by IntelliJ IDEA.
 * User: Medcl'
 * Date: 12-5-21
 * Time: 下午5:53
 */

public class TermItem{
    String term;
    int startOffset;
    int endOffset;
    public TermItem(String term,int startOffset,int endOffset){
        this.term=term;
        this.startOffset=startOffset;
        this.endOffset=endOffset;
    }

    @Override
    public String toString() {
        return term;
    }
}
