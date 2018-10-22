package org.elasticsearch.index.analysis;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * 拼音串切分，很难做到最好，认为取最少切分是最好的
 *
 * @author shenyanchao
 * @since 2018-10-08 12:22
 */
public class PinyinAlphabetTokenizerTest {

    @Test
    public void walk() throws Exception {

        Assert.assertEquals(Arrays.asList("xian").toString(), PinyinAlphabetTokenizer.walk("xian").toString());
        Assert.assertEquals(Arrays.asList("wo", "shi", "liang").toString(),
                PinyinAlphabetTokenizer.walk("woshiliang").toString());

        Assert.assertEquals(Arrays.asList("zhong", "hua", "ren", "min", "gong", "he", "guo").toString(),
                PinyinAlphabetTokenizer.walk("zhonghuarenmingongheguo").toString());
        Assert.assertEquals(
                Arrays.asList("5", "zhong", "hua", "ren", "89", "min", "gong", "he", "guo", "234").toString(),
                PinyinAlphabetTokenizer.walk("5zhonghuaren89mingongheguo234").toString());
    }

}