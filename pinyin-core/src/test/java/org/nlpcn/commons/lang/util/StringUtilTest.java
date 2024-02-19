package org.nlpcn.commons.lang.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

	@Test
	public void test() {
		// 顺路介绍下 字符串是否为空的判断
		System.out.println(StringUtil.isBlank(" \t")); // result --> true

		// html清理
		System.out.println(StringUtil.rmHtmlTag("<a>hello ansj</a>my name is ")); // result
																					// -->hello
																					// ansj

		System.out.println(StringUtil.rmHtmlTag("hello ansj hello kk "));

		// 将用都好隔开的字符转换为sql中的in查询
		System.out.println(StringUtil.makeSqlInString("ansj,2134,123,123,123"));
		// result --> 'ansj','2134','123','123','123'
	}

	@Test
	public void joinerTest() {
		int[] ints = new int[] { 1, 2, 3, 4, 5, 6, 7 };
		Assert.assertEquals(StringUtil.joiner(ints, ","), "1,2,3,4,5,6,7");

		List<Integer> list = new ArrayList<Integer>();

		for (int i : ints) {
			list.add(i);
		}

		Assert.assertEquals(StringUtil.joiner(list, ","), "1,2,3,4,5,6,7");
	}

	@Test
	public void trimTest(){
		String str = new String(new char[]{(char)65279,'\u00A0','\u3000'}) ;
		
		Assert.assertEquals(StringUtil.trim(str).length(), 0);
	}

}
