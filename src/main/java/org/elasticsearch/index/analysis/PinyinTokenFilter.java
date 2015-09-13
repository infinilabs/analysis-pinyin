package org.elasticsearch.index.analysis;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.io.IOException;

/**
 */
public class PinyinTokenFilter extends TokenFilter {

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
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
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder firstLetters = new StringBuilder();
        for (int i = 0; i < bufferLength; i++) {
            char c = buffer[i];
            if (c < 128) {
                stringBuilder.append(c);
                firstLetters.append(c);
            } else {
                try {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (strs != null) {
                        //get first result by default
                        String first_value = strs[0];
                        //TODO more than one pinyin
                        stringBuilder.append(first_value);
                        if (this.padding_char.length() > 0) {
                            stringBuilder.append(this.padding_char);
                        }
                        firstLetters.append(first_value.charAt(0));

                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }
        }

        StringBuilder pinyinStringBuilder = new StringBuilder();
        if (first_letter.equals("prefix")) {
            pinyinStringBuilder.append(firstLetters.toString());
            if (this.padding_char.length() > 0) {
                pinyinStringBuilder.append(this.padding_char); //TODO splitter
            }
            pinyinStringBuilder.append(stringBuilder.toString());
        } else if (first_letter.equals("append")) {
            pinyinStringBuilder.append(stringBuilder.toString());
            if (this.padding_char.length() > 0) {
                if (!stringBuilder.toString().endsWith(this.padding_char)) {
                    pinyinStringBuilder.append(this.padding_char);
                }
            }
            pinyinStringBuilder.append(firstLetters.toString());
        } else if (first_letter.equals("none")) {
            pinyinStringBuilder.append(stringBuilder.toString());
        } else if (first_letter.equals("only")) {
            pinyinStringBuilder.append(firstLetters.toString());
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

    @Override
    public final void end() throws IOException {
        // set final offset
      super.end();
    }

    @Override
    public void reset() throws IOException {
        super.reset();
    }


}
