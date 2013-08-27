/**
 * 
 */
package org.elasticsearch.index.analysis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author matrix
 * 
 */
public class PinyinUtil {

    public static String[] removeDuplicates(String[] list) {
        Set<String> items = new HashSet<String>();
        for (String item : list) {
            if (!items.contains(item)) {
                items.add(item);
            }
        }
        return items.toArray(new String[items.size()]);
    }

    /**
     * 递归得到所有拼音组合
     * 
     * @param strJaggedArray
     * @return
     */
    public static List<String> exchange(String[][] strJaggedArray) {
        String[][] temp = doExchange(strJaggedArray);
        return Arrays.asList(temp[0]);
    }

    /**
     * 递归
     * 
     * @author wyh
     * @param strJaggedArray
     * @return
     */
    private static String[][] doExchange(String[][] strJaggedArray) {
        int len = strJaggedArray.length;
        if (len >= 2) {
            int len1 = strJaggedArray[0].length;
            int len2 = strJaggedArray[1].length;
            int newlen = len1 * len2;
            String[] temp = new String[newlen];
            int Index = 0;
            for (int i = 0; i < len1; i++) {
                for (int j = 0; j < len2; j++) {
                    temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];
                    Index++;
                }
            }
            String[][] newArray = new String[len - 1][];
            for (int i = 2; i < len; i++) {
                newArray[i - 1] = strJaggedArray[i];
            }
            newArray[0] = temp;
            return doExchange(newArray);
        } else {
            return strJaggedArray;
        }
    }

}
