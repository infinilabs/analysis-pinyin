package org.nlpcn.commons.lang.util;

import org.junit.Test;


/**
 * 
 * @author ansj
 *
 */
public class WordAlertTest {

	@Test
	public void test() {
		String str = "ａｚ ＡＺ AZ az ０９•" ;
		char[] result = WordAlert.alertStr(str) ;
		System.out.println(new String(result));//az az az az 09·
	}

}
