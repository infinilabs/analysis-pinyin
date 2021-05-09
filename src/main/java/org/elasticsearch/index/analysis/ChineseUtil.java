package org.elasticsearch.index.analysis;

import org.nlpcn.commons.lang.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ChineseUtil {
    /**
     * 汉字始
     */
    public static char CJK_UNIFIED_IDEOGRAPHS_START = '\u4E00';
    /**
     * 汉字止
     */
    public static char CJK_UNIFIED_IDEOGRAPHS_END = '\u9FA5';

    public static List<String> segmentChinese(String str){
        if (StringUtil.isBlank(str)) {
            return Collections.emptyList();
        }

        List<String> lists = str.length()<=32767?new ArrayList<>(str.length()):new LinkedList<>();
        for (int i=0;i<str.length();i++){
            char c = str.charAt(i);
            if(c>=CJK_UNIFIED_IDEOGRAPHS_START&&c<=CJK_UNIFIED_IDEOGRAPHS_END){
                lists.add(String.valueOf(c));
            }
            else{
                lists.add(null);
            }

        }
        return lists;
    }
}
