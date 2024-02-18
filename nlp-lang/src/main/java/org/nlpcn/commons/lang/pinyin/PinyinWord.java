package org.nlpcn.commons.lang.pinyin;

public class PinyinWord {
	public String py;
	public int tone;

	PinyinWord(String pinyinStr) {
		this.py = pinyinStr.substring(0, pinyinStr.length() - 1);
		char c = pinyinStr.charAt(pinyinStr.length() - 1);
		if (c >= '0' && c <= '9') {
			this.tone = Integer.parseInt(String.valueOf(c));
		} else {
			this.py = pinyinStr;
		}
	}

	public PinyinWord(char c) {
		this.py = String.valueOf(c);
	}

	public String toString() {
		if (tone > 0)
			return this.py + tone;
		else
			return this.py;
	}

	public static void main(String[] args) {
		System.out.println(new PinyinWord("bei3"));
		;
	}
}
