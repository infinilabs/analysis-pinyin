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
    private String padding_char;
    private String first_letter;

    @Override
    public final boolean incrementToken() throws IOException {

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

        StringBuilder pinyinStringBuilder = new StringBuilder();
        // let's join them
        if (first_letter.equals("all")) {
            pinyinStringBuilder.append(buffer).append(this.padding_char);
            pinyinStringBuilder.append(PinyinUtil.exchange(tmpFirst, this.padding_char));
            if (this.padding_char.length() > 0) {
                pinyinStringBuilder.append(this.padding_char);
            }
            pinyinStringBuilder.append(PinyinUtil.exchange(tmpFull, this.padding_char));
        } else if (first_letter.equals("prefix")) {
            pinyinStringBuilder.append(PinyinUtil.exchange(tmpFirst, this.padding_char));
            if (this.padding_char.length() > 0) {
                pinyinStringBuilder.append(this.padding_char);
            }
            pinyinStringBuilder.append(PinyinUtil.exchange(tmpFull, this.padding_char));
        } else if (first_letter.equals("append")) {
            String full = PinyinUtil.exchange(tmpFull, this.padding_char);
            pinyinStringBuilder.append(full);
            if (this.padding_char.length() > 0) {
                if (!full.endsWith(this.padding_char)) {
                    pinyinStringBuilder.append(this.padding_char);
                }
            }
            pinyinStringBuilder.append(PinyinUtil.exchange(tmpFirst, this.padding_char));
        } else if (first_letter.equals("none")) {
            pinyinStringBuilder.append(PinyinUtil.exchange(tmpFull, this.padding_char));
        } else if (first_letter.equals("only")) {
            pinyinStringBuilder.append(PinyinUtil.exchange(tmpFirst, this.padding_char));
        }
        termAtt.setEmpty();
        termAtt.resizeBuffer(pinyinStringBuilder.length());
        termAtt.append(pinyinStringBuilder);
        termAtt.setLength(pinyinStringBuilder.length());
        return true;
    }

    public PinyinTokenFilter(TokenStream in, String padding_char, String first_letter) {
        super(in);
        this.padding_char = padding_char;
        this.first_letter = first_letter;
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

}
