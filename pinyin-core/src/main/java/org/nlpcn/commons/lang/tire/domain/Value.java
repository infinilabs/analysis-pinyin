package org.nlpcn.commons.lang.tire.domain;

import java.util.Arrays;

public class Value {
	private static final String TAB = "\t";
	private String keyword;
	private String[] paramers = new String[0];

	public Value(String keyword, String... paramers) {
		this.keyword = keyword;
		if (paramers != null) {
			this.paramers = paramers;
		}
	}

	public Value(String temp) {
		String[] strs = temp.split(TAB);
		this.keyword = strs[0];
		if (strs.length > 1) {
			this.paramers = Arrays.copyOfRange(strs, 1, strs.length);
		}
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String[] getParamers() {
		return paramers;
	}

	public void setParamers(String[] paramers) {
		this.paramers = paramers;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(keyword);
		for (int i = 0; i < paramers.length; i++) {
			sb.append(TAB);
			sb.append(paramers[i]);
		}
		return sb.toString();
	}

}
