package org.nlpcn.commons.lang.tire.splitWord;

import org.nlpcn.commons.lang.tire.SmartGetWord;
import org.nlpcn.commons.lang.tire.domain.SmartForest;
import org.nlpcn.commons.lang.util.StringUtil;

public class AllWordTest {
	public static void main(String[] args) {
		/**
		 * 词典的构造.一行一个词后面是参数.可以从文件读取.可以是read流.
		 */
		long start = System.currentTimeMillis();
		SmartForest<Integer> forest = new SmartForest<Integer>();

		forest.add("中国", 3);

		forest.add("android", 3);

		forest.add("java", 3);
		
		forest.add("jav", 3);

		forest.add("中国人", 3);
		forest.add("国人", 3);
		
		forest.add("0",3);
		forest.add("3",3);

		String content = " Android-java-中国人00000000000000 1230 013 33333";
		
		
		content = StringUtil.rmHtmlTag(content);

		for (int i = 0; i < 1; i++) {
			SmartGetWord<Integer> udg = forest.getWord(content.toLowerCase().toCharArray());

			String temp;
			while ((temp = udg.getAllWords()) != null) {
				System.out.println(temp + "\t" + udg.getParam());
			}
		}
		System.out.println(System.currentTimeMillis() - start);
	}
}
