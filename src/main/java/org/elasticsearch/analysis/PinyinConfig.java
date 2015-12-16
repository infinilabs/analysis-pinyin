package org.elasticsearch.analysis;

/**
 * Created by medcl on 15/11/26.
 */
public class PinyinConfig {

    public enum Mode{
        onlyFirstLetter(1),
        fullPinyin(2),
        fullPinyinWithSpace(3),
        supportPolyphony(4);

        private final int value;
        Mode(int i) {
            value=i;
        }
    }
}
