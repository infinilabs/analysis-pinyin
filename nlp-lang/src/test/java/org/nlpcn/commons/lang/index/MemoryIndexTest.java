package org.nlpcn.commons.lang.index;

import org.junit.Test;
import org.nlpcn.commons.lang.pinyin.Pinyin;

public class MemoryIndexTest {

	@Test
	public void test() {
		MemoryIndex<String> mi = new MemoryIndex<String>();
		
//		FileIterator instanceFileIterator = IOUtil.instanceFileIterator("/home/ansj/workspace/ansj_seg/library/default.dic", IOUtil.UTF8);
//
		long start = System.currentTimeMillis();
//		System.out.println("begin init!");
//		while (instanceFileIterator.hasNext()) {
//			String temp = instanceFileIterator.next();
//			temp = temp.split("\t")[0] ;
//			
//			// temp 是提示返回的元素
//			// temp 增加到搜索提示
//			// mi.str2QP("字符串转全拼")  比如 “中国” --》 "zhongguo"
//			// new String(Pinyin.str2FirstCharArr(temp)) 字符串首字母拼音  比如 “中国” --》 "zg"
//			mi.addItem(temp, temp ,mi.str2QP(temp), new String(Pinyin.str2FirstCharArr(temp)));
//		}
		
		//增加新词
		String temp = "中国" ;
		
		//生成各需要建立索引的字段
		String quanpin = mi.str2QP(temp) ; //zhongguo
        String jianpinpin = Pinyin.list2String(Pinyin.firstChar(temp), ""); //zg
		
		//增加到索引中
		mi.addItem(temp, temp ,quanpin,jianpinpin);

		//搜索提示
		System.out.println(mi.suggest("zg"));
		System.out.println(mi.suggest("zhongguo"));
		System.out.println(mi.suggest("中国"));
		System.out.println(mi.smartSuggest("中过"));

		System.out.println("init ok use time " + (System.currentTimeMillis() - start));

	}
}
