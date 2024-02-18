package org.nlpcn.commons.lang.tire.splitWord;

import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import org.nlpcn.commons.lang.util.StringUtil;

import java.io.BufferedReader;
import java.io.StringReader;

/**
 * Created by ansj on 3/30/14.
 */
public class GetWordTest {
	public static void main(String[] args) throws Exception {
		/**
		 * 词典的构造.一行一个词后面是参数.可以从文件读取.可以是read流.
		 */
		long start = System.currentTimeMillis();
		String dic = "android\t10\t孙健\nc\t100\nC++\t10\nc++\t5\nc#\t100\nVC++\t100".toLowerCase();
		System.out.println(dic);
		Forest forest = Library.makeForest(new BufferedReader(new StringReader(dic)));
		/**
		 * 删除一个单词
		 */
		Library.removeWord(forest, "中国");
		/**
		 * 增加一个新词
		 */
		Library.insertWord(forest, "中国人");
		String content = "Android--中国人";
		content = StringUtil.rmHtmlTag(content);

		for (int i = 0; i < 1; i++) {
			GetWord udg = forest.getWord(content.toLowerCase().toCharArray());

			String temp = null;
			while ((temp = udg.getFrontWords()) != null) {
				System.out.println(temp + "\t\t" + udg.getParam()[0] + "\t\t" + udg.getParam()[1]);
				System.out.println(udg.offe);
			}
		}
		System.out.println(System.currentTimeMillis() - start);
	}
}
