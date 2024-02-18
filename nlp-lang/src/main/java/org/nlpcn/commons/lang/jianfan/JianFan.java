package org.nlpcn.commons.lang.jianfan;

/**
 * 简体繁体相互转换
 * Created by ansj on 4/1/14.
 */
public class JianFan {


	/**
	 * 简体转繁体
	 * @param str
	 * @return
	 */
	public static String j2f(String str) {
		return Converter.TRADITIONAL.convert(str) ;
	}

	/**
	 * 繁体转简体
	 * @param str
	 * @return
	 */
	public static String f2j(String str) {
	    return Converter.SIMPLIFIED.convert(str) ;
	}


}
