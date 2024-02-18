/** 
 * File    : CaseType.java 
 * Created : 2014年1月22日 
 * By      : luhuiguo 
 */
package org.nlpcn.commons.lang.pinyin;

/**
 * Define the output case of Pinyin string
 * 
 * <p>
 * This class provides several options for outputted cases of Pinyin string,
 * which are listed below. For example, Chinese character '民'
 * 
 * <table>
 * <tr>
 * <th>Options</th>
 * <th>Output</th>
 * </tr>
 * <tr>
 * <td>LOWERCASE</td>
 * <td align = "center">min2</td>
 * </tr>
 * <tr>
 * <td>UPPERCASE</td>
 * <td align = "center">MIN2</td>
 * </tr>
 * <tr>
 * <td>CAPITALIZE</td>
 * <td align = "center">Min2</td>
 * </tr>
 * </table>
 * 
 * @author luhuiguo
 */
public enum CaseType {

	/**
	 * '民' -> min2
	 */
	LOWERCASE,

	/**
	 * '民' -> MIN2
	 */
	UPPERCASE,

	/**
	 * '民' -> Min2
	 */
	CAPITALIZE;

}
