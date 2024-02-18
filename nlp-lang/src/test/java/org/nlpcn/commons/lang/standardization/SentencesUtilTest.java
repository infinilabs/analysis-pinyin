package org.nlpcn.commons.lang.standardization;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class SentencesUtilTest {

	@Test
	public void test() {
		SentencesUtil su = new SentencesUtil() ;
		List<String> sentenceList = su.toSentenceList("中国\n\n\r\r123.1 你好。123 hello word . hello word") ;
		
		for (String string : sentenceList) {
			System.out.println(string);
		}
	}

}
