/** 
 * File    : ToneType.java 
 * Created : 2014年1月22日 
 * By      : luhuiguo 
 */
package org.nlpcn.commons.lang.pinyin;

/**
 * Define the output format of Pinyin tones
 * 
 * <p>
 * Chinese has four pitched tones and a "toneless" tone. They are called Píng(平,
 * flat), Shǎng(上, rise), Qù(去, high drop), Rù(入, drop) and Qing(轻, toneless).
 * Usually, we use 1, 2, 3, 4 and 5 to represent them.
 * 
 * <p>
 * This class provides several options for output of Chinese tones, which are
 * listed below. For example, Chinese character '打'
 * 
 * <table>
 * <tr>
 * <th>Options</th>
 * <th>Output</th>
 * </tr>
 * <tr>
 * <td>WITH_TONE_NUMBER</td>
 * <td align = "center">da3</td>
 * </tr>
 * <tr>
 * <td>WITHOUT_TONE</td>
 * <td align = "center">da</td>
 * </tr>
 * <tr>
 * <td>WITH_TONE_MARK</td>
 * <td align = "center">dǎ</td>
 * </tr>
 * </table>
 * 
 * @author luhuiguo
 */
public enum ToneType {

	/**
	 * '打' -> da3
	 */
	WITH_TONE_NUMBER,

	/**
	 * '打' -> da
	 */
	WITHOUT_TONE,

	/**
	 * '打' -> dǎ
	 */
	WITH_TONE_MARK,
	
	/**
	 * '打' -> d
	 */
	WITH_ABBR;

}
