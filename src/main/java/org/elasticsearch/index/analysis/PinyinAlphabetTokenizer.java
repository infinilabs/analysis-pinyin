package org.elasticsearch.index.analysis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by medcl on 16/10/13.
 */
public class PinyinAlphabetTokenizer {

    private static final int PINYIN_MAX_LENGTH = 6;

    public static List<String> walk(String text) {
        return segPinyinStr(text);
    }

    private static List<String> segPinyinStr(String content) {
        String pinyinStr = content;
        pinyinStr = pinyinStr.toLowerCase();
        // 按非letter切分
        List<String> pinyinStrList = splitByNoletter(pinyinStr);
        List<String> pinyinList = new ArrayList<>();
        for (String pinyinText : pinyinStrList) {
            if (pinyinText.length() == 1) {
                pinyinList.add(pinyinText);
            } else {
                List<String> forward = positiveMaxMatch(pinyinText, PINYIN_MAX_LENGTH);
                if (forward.size() == 1) { // 前向只切出1个的话，没有必要再做逆向分词
                    pinyinList.addAll(forward);
                } else {
                    // 分别正向、逆向最大匹配，选出最短的作为最优结果
                    List<String> backward = reverseMaxMatch(pinyinText, PINYIN_MAX_LENGTH);
                    if (forward.size() <= backward.size()) {
                        pinyinList.addAll(forward);
                    } else {
                        pinyinList.addAll(backward);
                    }
                }
            }
        }
        return pinyinList;
    }

    private static List<String> splitByNoletter(String pinyinStr) {
        List<String> pinyinStrList = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        boolean lastWord = true;
        for (char c : pinyinStr.toCharArray()) {
            if ((c > 96 && c < 123) || (c > 64 && c < 91)) {
                if (!lastWord){
                    pinyinStrList.add(sb.toString());
                    sb.setLength(0);
                }
                sb.append(c);
                lastWord = true;
            } else {
                if (lastWord & sb.length()>0) {
                    pinyinStrList.add(sb.toString());
                    sb.setLength(0);
                }
                sb.append(c);
                lastWord = false;
            }
        }
        if (sb.length() > 0) {
            pinyinStrList.add(sb.toString());
        }
        return pinyinStrList;

    }

    private static List<String> positiveMaxMatch(String pinyinText, int maxLength) {

        List<String> pinyinList = new ArrayList<>();
        StringBuffer noMatchBuffer = new StringBuffer();
        for (int start = 0; start < pinyinText.length(); ) {
            int end = start + maxLength;
            if (end > pinyinText.length()) {
                end = pinyinText.length();
            }
            if (start == end) {
                break;
            }
            String sixStr = pinyinText.substring(start, end);
            boolean match = false;
            for (int j = 0; j < sixStr.length(); j++) {
                String guess = sixStr.substring(0, sixStr.length() - j);
                if (PinyinAlphabetDict.getInstance().match(guess)) {
                    pinyinList.add(guess);
                    start += guess.length();
                    match = true;
                    break;
                }
            }
            if (!match) { //没命中,向后移动一位
                noMatchBuffer.append(sixStr.substring(0, 1));
                start++;
            }else { // 命中，加上之前没命中的，并清空
                if (noMatchBuffer.length() > 0) {
                    pinyinList.add(noMatchBuffer.toString());
                    noMatchBuffer.setLength(0);
                }
            }
        }
        if (noMatchBuffer.length() > 0) {
            pinyinList.add(noMatchBuffer.toString());
            noMatchBuffer.setLength(0);
        }

        return pinyinList;
    }

    private static List<String> reverseMaxMatch(String pinyinText, int maxLength) {
        List<String> pinyinList = new ArrayList<>();
        StringBuffer noMatchBuffer = new StringBuffer();
        for (int end = pinyinText.length(); end >= 0; ) {
            int start = end - maxLength;
            if (start < 0) {
                start = 0;
            }
            if (start == end) {
                break;
            }
            boolean match = false;
            String sixStr = pinyinText.substring(start, end);
            for (int j = 0; j < sixStr.length(); j++) {
                String guess = sixStr.substring(j);
                if (PinyinAlphabetDict.getInstance().match(guess)) {
                    pinyinList.add(guess);
                    end -= guess.length();
                    match = true;
                    break;
                }
            }
            if (!match) { //一个也没命中
                noMatchBuffer.append(sixStr.substring(sixStr.length() - 1));
                end--;
            } else {
                if (noMatchBuffer.length() > 0) {
                    pinyinList.add(noMatchBuffer.toString());
                    noMatchBuffer.setLength(0);
                }
            }
        }

        if (noMatchBuffer.length() > 0) {
            pinyinList.add(noMatchBuffer.toString());
            noMatchBuffer.setLength(0);
        }
        // reverse 保持切词顺序
        Collections.reverse(pinyinList);
        return pinyinList;
    }


}

 class PinyinAlphabetDict {

    private static final String fileName = "/pinyin_alphabet.dict";

    private Set<String> alphabet = new HashSet<String>();

    private static PinyinAlphabetDict instance;

    private PinyinAlphabetDict() {
        InputStream in = PinyinAlphabetDict.class.getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            String line;
            while (null != (line = reader.readLine())) {
                if (line.trim().length() > 0) {
                    alphabet.add(line);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("read pinyin dic error.", ex);
        } finally {
            try {
                reader.close();
            } catch (Exception ignored) {
            }
        }
    }

    public static PinyinAlphabetDict getInstance() {
        if (instance == null) {
            synchronized (PinyinAlphabetDict.class) {
                if (instance == null) {
                    instance = new PinyinAlphabetDict();
                }
            }
        }
        return instance;
    }

    public boolean match(String c) {
        return alphabet.contains(c);
    }
}
