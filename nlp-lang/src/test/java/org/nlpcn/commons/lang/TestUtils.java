package org.nlpcn.commons.lang;

import java.util.List;

import org.nlpcn.commons.lang.pinyin.Pinyin;

public class TestUtils {

	public static String mainResources(final String file) {
		return System.getProperties().getProperty("user.dir") + "/src/main/resources"
				+ (file.startsWith("/") ? file : "/" + file);
	}

	public static String testResources(final String file) {
		return System.getProperties().getProperty("user.dir") + "/src/test/resources"
				+ (file.startsWith("/") ? file : "/" + file);
	}

	public static void main(String[] args) {
		List<String> parseStr = Pinyin.unicodePinyin("日往月来");
		System.out.println(parseStr);
		parseStr = Pinyin.pinyin("日往月来");
		System.out.println(parseStr);
		parseStr = Pinyin.tonePinyin("日往月来");
		System.out.println(parseStr);
	}
}
