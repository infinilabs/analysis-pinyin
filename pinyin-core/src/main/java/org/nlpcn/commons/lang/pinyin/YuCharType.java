/** 
 * File    : YuCharType.java 
 * Created : 2014年1月22日 
 * By      : luhuiguo 
 */
package org.nlpcn.commons.lang.pinyin;

/**
 * Define the output format of character 'ü'
 * 
 * <p>
 * 'ü' is a special character of Hanyu Pinyin, which can not be simply
 * represented by English letters. In Hanyu Pinyin, such characters include 'ü',
 * 'üe', 'üan', and 'ün'.
 * 
 * <p>
 * This class provides several options for output of 'ü', which are listed
 * below.
 * 
 * <table>
 * <tr>
 * <th>Options</th>
 * <th>Output</th>
 * </tr>
 * <tr>
 * <td>WITH_U_AND_COLON</td>
 * <td align = "center">u:</td>
 * </tr>
 * <tr>
 * <td>WITH_V</td>
 * <td align = "center">v</td>
 * </tr>
 * <tr>
 * <td>WITH_U_UNICODE</td>
 * <td align = "center">ü</td>
 * </tr>
 * </table>
 * 
 * @author luhuiguo
 */
public enum YuCharType {

	/**
	 * The option indicates that the output of 'ü' is "u:".
	 */
	WITH_U_AND_COLON,

	/**
	 * The option indicates that the output of 'ü' is "v".
	 */
	WITH_V,

	/**
	 * The option indicates that the output of 'ü' is "ü" in Unicode form.
	 */
	WITH_U_UNICODE;

}
