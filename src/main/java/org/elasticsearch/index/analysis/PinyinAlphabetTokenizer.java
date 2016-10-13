package org.elasticsearch.index.analysis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by medcl on 16/10/13.
 */
public class PinyinAlphabetTokenizer {

        public static List<String> walk(String text) {
            int maxLength=6;
            text = text.toLowerCase();
            LinkedList<String> candidates=new LinkedList<>();
            StringBuffer buffer=new StringBuffer();
            boolean lastWord=true;
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if ((c > 96 && c < 123) || (c > 64 && c < 91)) {
                    if(!lastWord){
                        String str = buffer.toString();
                        buffer.setLength(0);
                        candidates.add(str);
                    }
                    buffer.append(c);
                    lastWord=true;
                }else{
                    //meet non letter
                    if(lastWord){
                        parse(candidates, buffer);
                        if(buffer.length()>0){
                            String str = buffer.toString();
                            buffer.setLength(0);
                            candidates.add(str);
                        }
                    }
                    buffer.append(c);
                    lastWord=false;
                }

                //start to check pinyin
                if(buffer.length()>=maxLength){
                    parse(candidates, buffer);
                }
            }

            //cleanup
            if(lastWord){
                parse(candidates,buffer);
            }

            //final cleanup
            if(buffer.length()>0){
                candidates.add(buffer.toString());
            }

            return candidates;
        }

    private static void parse(LinkedList<String> candidates, StringBuffer buffer) {
        for (int j = 0; j < buffer.length(); j++) {
            String guess=buffer.substring(0,buffer.length()-j);
            if(PinyinAlphabetDict.getInstance().match(guess)){
                candidates.add(guess);
                String left=buffer.substring(buffer.length()-j,buffer.length());
                buffer.setLength(0);
                buffer.append(left);
                break;
            }
        }
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
