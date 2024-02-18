package org.nlpcn.commons.lang.standardization;

import org.nlpcn.commons.lang.util.WordAlert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 句子标准化
 * 英文。数字全角转半角。。
 * 所有数字，EN合并
 *
 * @author ansj
 */

public class WordUtil {

	/**
	 * 用这个值替换所有数字，如果为null就不替换
	 */
	private Character num2Value;

	/*
	 * 用这个值替换所有英文，如果为null就不替换
	 */
	private Character en2Value;

	/**
	 * 如果不想被替换清保留null
	 *
	 * @param num2Value
	 * @param enValue
	 */
	public WordUtil(Character num2Value, Character enValue) {
		this.num2Value = num2Value;
		this.en2Value = enValue;
	}

	/**
	 * @param str
	 * @return
	 */
	public List<Element> str2Elements(String str) {

		if (str == null || str.trim().length() == 0) {
			return Collections.emptyList();
		}

		char[] chars = WordAlert.alertStr(str);
		int maxLen = chars.length - 1;
		List<Element> list = new ArrayList<Element>();
		Element element = null;
		out:
		for (int i = 0; i < chars.length; i++) {
			if (num2Value != null && chars[i] >= '0' && chars[i] <= '9') {
				element = new Element(num2Value);
				list.add(element);
				if (i == maxLen) {
					break out;
				}
				char c = chars[++i];
				while (c == '.' || c == '%' || (c >= '0' && c <= '9')) {
					if (i == maxLen) {
						break out;
					}
					c = chars[++i];
					element.len();
				}
				i--;
			} else if (en2Value != null && chars[i] >= 'a' && chars[i] <= 'z') {
				element = new Element(en2Value);
				list.add(element);
				if (i == maxLen) {
					break out;
				}
				char c = chars[++i];
				while (c >= 'a' && c <= 'z') {
					if (i == maxLen) {
						break out;
					}
					c = chars[++i];
					element.len();
				}
				i--;
			} else {
				list.add(new Element(chars[i]));
			}
		}
		return list;
	}


	/**
	 * @param str
	 * @return
	 */
	public String str2Str(String str) {
		return new String(str2Chars(str));

	}

	/**
	 * @param str
	 * @return
	 */
	public char[] str2Chars(String str) {
		List<Element> elements = str2Elements(str);
		char[] chars = new char[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			chars[i] = elements.get(i).name;
		}
		return chars;
	}

	public static void main(String[] args) {
		WordUtil wordUtil = new WordUtil('1', 'A');
		System.out.println(wordUtil.str2Elements("123中国CHINA456你好!"));
		System.out.println(Arrays.toString(wordUtil.str2Chars("123中国CHINA456你好!")));
		System.out.println(wordUtil.str2Str("123中国CHINA456你好!"));
	}
}
