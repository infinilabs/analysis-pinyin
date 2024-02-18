package org.nlpcn.commons.lang.tire;

import org.nlpcn.commons.lang.tire.domain.SmartForest;

public class SmartGetWord<T> {
	private static final String EMPTYSTRING = "";
	public int offe;
	byte status = 0;
	int root = 0;
	int i = this.root;
	boolean isBack = false;
	private SmartForest<T> forest;
	private char[] chars;
	private String str;
	private int tempOffe;
	private T param;
	private SmartForest<T> branch;

	public SmartGetWord(SmartForest<T> forest, String content) {
		this.chars = content.toCharArray();
		this.forest = forest;
		this.branch = forest;
	}

	public SmartGetWord(SmartForest<T> forest, char[] chars) {
		this.chars = chars;
		this.forest = forest;
		this.branch = forest;
	}

	public String getAllWords() {
		String temp = this.allWords();

		temp = checkNumberOrEnglish(temp);

		while (EMPTYSTRING.equals(temp)) {
			temp = this.allWords();
			temp = checkNumberOrEnglish(temp);
		}
		return temp;
	}

	/**
	 * 验证一个词语的左右边.不是英文和数字
	 * 
	 * @param temp
	 * @return
	 */
	private String checkNumberOrEnglish(String temp) {

		if (temp == null || temp == EMPTYSTRING) {
			return temp;
		}

		// 先验证最左面

		char l = temp.charAt(0);

		if (l < 127 && offe > 0) {
			if (checkSame(l, chars[offe - 1])) {
				return EMPTYSTRING;
			}
		}

		char r = l;

		if (temp.length() > 1) {
			r = temp.charAt(temp.length() - 1);
		}

		if (r < 127 && (offe + temp.length()) < chars.length) {
			if (checkSame(r, chars[offe + temp.length()])) {
				return EMPTYSTRING;
			}
		}

		return temp;
	}

	/**
	 * 验证两个char是否都是数字或者都是英文
	 * 
	 * @param l
	 * @param c
	 * @return
	 */
	private boolean checkSame(char l, char c) {

		if (isE(l) && isE(c)) {
			return true;
		}

		if (isNum(l) && isNum(c)) {
			return true;
		}

		return false;
	}

	public String getFrontWords() {
		String temp = null;
		do {
			temp = this.frontWords();
			temp = checkNumberOrEnglish(temp);
		} while (EMPTYSTRING.equals(temp));
		return temp;
	}

	private Integer tempJLen = null;

	private String allWords() {

		for (; i < chars.length;) {
			if (tempJLen == null) {
				branch = branch.getBranch(chars[i]);
			}
			if (branch == null) {
				branch = forest;
				i++;
				continue;
			}

			for (int j = i + (tempJLen == null ? 0 : tempJLen); j < chars.length; j++) {
				if (j > i) {
					branch = branch.getBranch(chars[j]);
				}
				if (branch == null) {
					branch = forest;
					i++;
					tempJLen = null;
					return EMPTYSTRING;
				}

				switch (branch.getStatus()) {
				case 2:
					offe = i;
					param = branch.getParam();
					tempJLen = j - i + 1;
					return new String(chars, i, j - i + 1);
				case 3:
					offe = i;
					param = branch.getParam();
					branch = forest;
					tempJLen = null;
					i++;
					return new String(chars, i - 1, j - i + 2);
				}

			}

			i++;
			branch = forest;
			tempJLen = null;
			return EMPTYSTRING;

		}

		return null;

	}

	private String frontWords() {
		for (; this.i < this.chars.length + 1; this.i++) {
			if (i == chars.length) {
				this.branch = null;
			} else {
				this.branch = this.branch.getBranch(this.chars[this.i]);
			}
			if (this.branch == null) {
				this.branch = this.forest;
				if (this.isBack) {
					this.offe = this.root;
					this.str = new String(this.chars, this.root, this.tempOffe);
					if (this.str.length() == 0) {
						this.root += 1;
						this.i = this.root;
					} else {
						this.i = (this.root + this.tempOffe);
						this.root = this.i;
					}
					this.isBack = false;
					return this.str;
				}
				this.i = this.root;
				this.root += 1;
			} else {
				switch (this.branch.getStatus()) {
				case 2:
					this.isBack = true;
					this.tempOffe = (this.i - this.root + 1);
					this.param = this.branch.getParam();
					break;
				case 3:
					this.offe = this.root;
					this.str = new String(this.chars, this.root, this.i - this.root + 1);
					String temp = this.str;
					this.param = this.branch.getParam();
					this.branch = this.forest;
					this.isBack = false;
					if (temp.length() > 0) {
						this.i += 1;
						this.root = this.i;
					} else {
						this.i = (this.root + 1);
					}
					return this.str;
				}
			}
		}
		this.tempOffe += this.chars.length;
		return null;
	}

	public boolean isE(char c) {
		if ((c >= 'A') && (c <= 'z')) {
			return true;
		}
		return false;
	}

	public boolean isNum(char c) {
		if ((c >= '0') && (c <= '9')) {
			return true;
		}
		return false;
	}

	public void reset(String content) {
		this.offe = 0;
		this.status = 0;
		this.root = 0;
		this.i = this.root;
		this.isBack = false;
		this.tempOffe = 0;
		this.chars = content.toCharArray();
		this.branch = this.forest;
	}

	/**
	 * 参数
	 *
	 * @return
	 */
	public T getParam() {
		return this.param;
	}

}
