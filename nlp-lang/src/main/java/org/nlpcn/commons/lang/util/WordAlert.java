package org.nlpcn.commons.lang.util;

public class WordAlert {

	/**
	 * 这个就是(int)'ａ'
	 */
	public static final int MIN_LOWER = 65345;
	/**
	 * 这个就是(int)'ｚ'
	 */
	public static final int MAX_LOWER = 65370;
	/**
	 * 差距进行转译需要的
	 */
	public static final int LOWER_GAP = 65248;
	/**
	 * 这个就是(int)'Ａ'
	 */
	public static final int MIN_UPPER = 65313;
	/**
	 * 这个就是(int)'Ｚ'
	 */
	public static final int MAX_UPPER = 65338;
	/**
	 * 差距进行转译需要的
	 */
	public static final int UPPER_GAP = 65216;
	/**
	 * 这个就是(int)'A'
	 */
	public static final int MIN_UPPER_E = 65;
	/**
	 * 这个就是(int)'Z'
	 */
	public static final int MAX_UPPER_E = 90;
	/**
	 * 差距进行转译需要的
	 */
	public static final int UPPER_GAP_E = -32;
	/**
	 * 这个就是(int)'０'
	 */
	public static final int MIN_UPPER_N = 65296;
	/**
	 * 这个就是(int)'９'
	 */
	public static final int MAX_UPPER_N = 65305;
	/**
	 * 差距进行转译需要的
	 */
	public static final int UPPER_GAP_N = 65248;

	private static final char[] CHARCOVER = new char[65536];

	static {
		for (int i = 0; i < CHARCOVER.length; i++) {
			if (i >= MIN_LOWER && i <= MAX_LOWER) {
				CHARCOVER[i] = (char) (i - LOWER_GAP);
			} else if (i >= MIN_UPPER && i <= MAX_UPPER) {
				CHARCOVER[i] = (char) (i - UPPER_GAP);
			} else if (i >= MIN_UPPER_E && i <= MAX_UPPER_E) {
				CHARCOVER[i] = (char) (i - UPPER_GAP_E);
			} else if (i >= MIN_UPPER_N && i <= MAX_UPPER_N) {
				CHARCOVER[i] = (char) (i - UPPER_GAP_N);
			} else if (i >= '0' && i <= '9') {
				CHARCOVER[i] = (char) i;
			} else if (i >= 'a' && i <= 'z') {
				CHARCOVER[i] = (char) i;
			}

//			CHARCOVER['﹩'] = '$';
//			CHARCOVER['　'] = ' ';
//			CHARCOVER['，'] = ',';
//			CHARCOVER['？'] = '?';
//			CHARCOVER['“'] = '"' ;
//			CHARCOVER['”'] = '"' ;
			
			
		}
	}

	/**
	 * 对全角的字符串,大写字母进行转译.如ｓｄｆｓｄｆ
	 * 
	 * @param chars
	 * @param start
	 * @param end
	 * @return
	 */
	public static String alertEnglish(char[] chars, int start, int end) {
		for (int i = start; i < start + end; i++) {
			if (chars[i] >= MIN_LOWER && chars[i] <= MAX_LOWER) {
				chars[i] = (char) (chars[i] - LOWER_GAP);
			}
			if (chars[i] >= MIN_UPPER && chars[i] <= MAX_UPPER) {
				chars[i] = (char) (chars[i] - UPPER_GAP);
			}
			if (chars[i] >= MIN_UPPER_E && chars[i] <= MAX_UPPER_E) {
				chars[i] = (char) (chars[i] - UPPER_GAP_E);
			}
		}
		return new String(chars, start, end);
	}

	public static String alertEnglish(String temp, int start, int end) {
		char c = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < start + end; i++) {
			c = temp.charAt(i);
			if (c >= MIN_LOWER && c <= MAX_LOWER) {
				sb.append((char) (c - LOWER_GAP));
			} else if (c >= MIN_UPPER && c <= MAX_UPPER) {
				sb.append((char) (c - UPPER_GAP));
			} else if (c >= MIN_UPPER_E && c <= MAX_UPPER_E) {
				sb.append((char) (c - UPPER_GAP_E));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String alertNumber(char[] chars, int start, int end) {
		for (int i = start; i < start + end; i++) {
			if (chars[i] >= MIN_UPPER_N && chars[i] <= MAX_UPPER_N) {
				chars[i] = (char) (chars[i] - UPPER_GAP_N);
			}
		}
		return new String(chars, start, end);
	}

	public static String alertNumber(String temp, int start, int end) {
		char c = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < start + end; i++) {
			c = temp.charAt(i);
			if (c >= MIN_UPPER_N && c <= MAX_UPPER_N) {
				sb.append((char) (c - UPPER_GAP_N));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 将一个字符串标准化
	 * 
	 * @param str
	 * @return
	 */
	public static char[] alertStr(String str) {
		char[] chars = new char[str.length()];
		char c = 0;
		for (int i = 0; i < chars.length; i++) {
			c = CHARCOVER[str.charAt(i)];
			if (c > 0) {
				chars[i] = c;
			} else {
				chars[i] = str.charAt(i);
			}
		}
		return chars;
	}

	/**
	 * 判断一个字符串是否是english
	 * 
	 * @param word
	 * @return
	 */
	public static boolean isEnglish(String word) {
		int length = word.length();
		char c;
		for (int i = 0; i < length; i++) {
			c = word.charAt(i);
			if ((c >= 'a' && c <= 'z') || (c >= MIN_LOWER && c <= MAX_LOWER) || (c >= MIN_UPPER && c <= MAX_UPPER) || (c >= MIN_UPPER_E && c <= MAX_UPPER_E)) {
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断一个字符串是否是数字
	 * 
	 * @param word
	 * @return
	 */
	public static boolean isNumber(String word) {
		char c = 0;
		int len = word.length();
		for (int i = 0; i < len; i++) {
			c = word.charAt(i);
			if ((c >= '0' && c <= '9') || c >= MIN_UPPER_N && c <= MAX_UPPER_N || c == '.') {
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将一个char标准化
	 * 
	 * @param c
	 * @return
	 */
	public static char CharCover(char c) {
		return CHARCOVER[c];
	}

}
