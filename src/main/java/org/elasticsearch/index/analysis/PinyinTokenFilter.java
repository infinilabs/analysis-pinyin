package org.elasticsearch.index.analysis;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 */
public class PinyinTokenFilter extends TokenFilter {

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    private String mode;
    private List<String> array = null;
    private Iterator<String> tokenIter = null;

    @Override
    public final boolean incrementToken() throws IOException {
        if (tokenIter == null || !tokenIter.hasNext()) {
            if (!input.incrementToken()) {
                return false;
            }
            final char[] buffer = termAtt.buffer();
            final int bufferLength = termAtt.length();
            String[][] tmpFull = new String[bufferLength][];
            String[][] tmpFirst = new String[bufferLength][];
            for (int i = 0; i < bufferLength; i++) {
                char c = buffer[i];
                // 是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
                try {
                    tmpFull[i] = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (tmpFull[i] != null) {
                        tmpFull[i] = PinyinUtil.removeDuplicates(tmpFull[i]);
                        List<String> firstLetters = new ArrayList<String>();
                        for (String py : tmpFull[i]) {
                            String firstLetter = py.substring(0, 1);
                            if (!firstLetters.contains(firstLetter)) firstLetters.add(firstLetter);
                        }
                        tmpFirst[i] = firstLetters.toArray(new String[firstLetters.size()]);
                    } else {
                        tmpFull[i] = new String[] {String.valueOf(c)};
                        tmpFirst[i] = new String[] {String.valueOf(c)};
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }

            // let's join them
            if (mode.equals("all")) {
                array = new ArrayList<String>();
                array.add(termAtt.toString());
                array.addAll(PinyinUtil.exchange(tmpFirst));
                array.addAll(PinyinUtil.exchange(tmpFull));
            } else if (mode.equals("normal")) {
                array = new ArrayList<String>();
                array.addAll(PinyinUtil.exchange(tmpFirst));
                array.addAll(PinyinUtil.exchange(tmpFull));
            } else if (mode.equals("full_only")) {
                array = PinyinUtil.exchange(tmpFull);
            } else if (mode.equals("first_only")) {
                array = PinyinUtil.exchange(tmpFirst);
            }
            tokenIter = array.iterator();

            if (!tokenIter.hasNext()) return false;
        }

        clearAttributes();

        String token = tokenIter.next();
        termAtt.copyBuffer(token.toCharArray(), 0, token.length());
        return true;
    }

    public PinyinTokenFilter(TokenStream in, String mode) {
        super(in);
        this.mode = mode;
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        tokenIter = null;
    }

}
