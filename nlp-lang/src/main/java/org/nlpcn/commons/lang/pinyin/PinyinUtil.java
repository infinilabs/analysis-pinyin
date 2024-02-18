/** 
 * File    : Pinyin.java 
 * Created : 2014年1月22日 
 * By      : luhuiguo 
 */
package org.nlpcn.commons.lang.pinyin;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.nlpcn.commons.lang.tire.SmartGetWord;
import org.nlpcn.commons.lang.tire.domain.SmartForest;
import org.nlpcn.commons.lang.util.StringUtil;

/**
 * 
 * @author luhuiguo
 * @author ansj
 */
enum PinyinUtil {

	INSTANCE;

	public static final String PINYIN_MAPPING_FILE = "/pinyin.txt";
	public static final String POLYPHONE_MAPPING_FILE = "/polyphone.txt";

	public static final String EMPTY = "";
	public static final String SHARP = "#";
	public static final String EQUAL = "=";
	public static final String COMMA = ",";
	public static final String SPACE = " ";

	public static final char CJK_UNIFIED_IDEOGRAPHS_START = '\u4E00';
	public static final char CJK_UNIFIED_IDEOGRAPHS_END = '\u9FA5';

	private List<String> pinyinDict = null;

	private SmartForest<String[]> polyphoneDict = null;

	private int maxLen = 2;

	PinyinUtil() {
		loadPinyinMapping();
		loadPolyphoneMapping();
	}

	public void loadPinyinMapping() {

		pinyinDict = new ArrayList<String>();

		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(getClass().getResourceAsStream(PINYIN_MAPPING_FILE)), StandardCharsets.UTF_8));
			String line = null;
			while (null != (line = in.readLine())) {
				if (line.length() == 0 || line.startsWith(SHARP)) {
					continue;
				}
				String[] pair = line.split(EQUAL);

				if (pair.length < 2) {
					pinyinDict.add(EMPTY);
				} else {
					pinyinDict.add(pair[1]);
				}
			}

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadPolyphoneMapping() {

		polyphoneDict = new SmartForest<String[]>();

		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(getClass().getResourceAsStream(POLYPHONE_MAPPING_FILE)), StandardCharsets.UTF_8));

			String line = null;
			while (null != (line = in.readLine())) {
				// line = line.trim();
				if (line.length() == 0 || line.startsWith(SHARP)) {
					continue;
				}
				String[] pair = line.split(EQUAL);

				if (pair.length < 2) {
					continue;
				}
				maxLen = maxLen < pair[0].length() ? pair[0].length() : maxLen;

				polyphoneDict.add(pair[0], pair[1].split(SPACE));

			}

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] toUnformattedPinyin(char ch) {

		if (ch >= CJK_UNIFIED_IDEOGRAPHS_START && ch <= CJK_UNIFIED_IDEOGRAPHS_END) {
			String pinyinStr = pinyinDict.get(ch - CJK_UNIFIED_IDEOGRAPHS_START);
			return pinyinStr.split(COMMA);

		} else {
			return null;
		}
	}

	public String[] toFormattedPinyin(char ch, PinyinFormat format) {
		String[] pinyinStrArray = toUnformattedPinyin(ch);
		if (null != pinyinStrArray) {
			for (int i = 0; i < pinyinStrArray.length; i++) {
				pinyinStrArray[i] = PinyinFormatter.formatPinyin(pinyinStrArray[i], format);
			}
			return pinyinStrArray;
		} else
			return null;
	}

	public String toPinyin(char ch) {
		String[] pinyinStrArray = toUnformattedPinyin(ch);

		if (null != pinyinStrArray && pinyinStrArray.length > 0) {
			return pinyinStrArray[0];
		}
		return null;
	}

	public String toPinyin(char ch, PinyinFormat format) {

		String[] pinyinStrArray = null;

		pinyinStrArray = toFormattedPinyin(ch, format);

		if (null != pinyinStrArray && pinyinStrArray.length > 0) {
			return pinyinStrArray[0];
		}
		return null;
	}

	public List<String> convert(String str, PinyinFormat format) {

		if (StringUtil.isBlank(str)) {
			return Collections.emptyList();
		}

		SmartGetWord<String[]> word = polyphoneDict.getWord(str);

		List<String> lists = new LinkedList<String>();

		String temp = null;
		int beginOffe = 0;
		while ((temp = word.getFrontWords()) != null) {

			for (int i = beginOffe; i < word.offe; i++) {
				lists.add(toPinyin(str.charAt(i), format));
			}

			for (String t : word.getParam()) {
				lists.add(PinyinFormatter.formatPinyin(t, format));

			}
			beginOffe = word.offe + temp.length();
		}

		if (beginOffe < str.length()) {
			for (int i = beginOffe; i < str.length(); i++) {
				lists.add(toPinyin(str.charAt(i), format));
			}
		}
		return lists;

	}

	/**
	 * 动态增加拼音到词典
	 * 
	 * @param word
	 * @param pinyins
	 */
	public void insertPinyin(String word, String[] pinyins) {
		polyphoneDict.add(word, pinyins);
	}
}
