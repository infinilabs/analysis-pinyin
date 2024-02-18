/** 
 * File    : PinyinFormatter.java 
 * Created : 2014年1月22日 
 * By      : luhuiguo 
 */
package org.nlpcn.commons.lang.pinyin;

/**
 * 
 * @author luhuiguo
 */
public class PinyinFormatter {

	public static String formatPinyin(String pinyinStr, PinyinFormat format) {

		if (ToneType.WITH_ABBR == format.getToneType()) {

			pinyinStr = abbr(pinyinStr);

		} else {

			if ((ToneType.WITH_TONE_MARK == format.getToneType())
					&& ((YuCharType.WITH_V == format.getYuCharType()) || (YuCharType.WITH_U_AND_COLON == format
							.getYuCharType()))) {
				// ToneType.WITH_TONE_MARK force YuCharType.WITH_U_UNICODE
				format.setYuCharType(YuCharType.WITH_U_UNICODE);

				// throw new BadPinyinFormatException(
				// "tone marks cannot be added to v or u:");
			}

			switch (format.getToneType()) {
			case WITHOUT_TONE:
				pinyinStr = pinyinStr.replaceAll("[1-5]", "");
				break;
			case WITH_TONE_MARK:
				pinyinStr = pinyinStr.replaceAll("u:", "v");
				pinyinStr = convertToneNumber2ToneMark(pinyinStr);
				break;

			default:
				break;

			}

			switch (format.getYuCharType()) {
			case WITH_V:
				pinyinStr = pinyinStr.replaceAll("u:", "v");
				break;
			case WITH_U_UNICODE:
				pinyinStr = pinyinStr.replaceAll("u:", "ü");
				break;

			default:
				break;

			}
		}

		switch (format.getCaseType()) {
		case UPPERCASE:
			pinyinStr = pinyinStr.toUpperCase();
			break;
		case CAPITALIZE:
			pinyinStr = capitalize(pinyinStr);
			break;

		default:
			break;

		}

		return pinyinStr;
	}

	public static String abbr(String str) {

		if (str == null || str.length() == 0) {
			return str;
		}

		return str.substring(0, 1);
	}

	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuilder(strLen)
				.append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	/**
	 * Convert tone numbers to tone marks using Unicode <br/>
	 * <br/>
	 * 
	 * <b>Algorithm for determining location of tone mark</b><br/>
	 * 
	 * A simple algorithm for determining the vowel on which the tone mark
	 * appears is as follows:<br/>
	 * 
	 * <ol>
	 * <li>First, look for an "a" or an "e". If either vowel appears, it takes
	 * the tone mark. There are no possible pinyin syllables that contain both
	 * an "a" and an "e".
	 * 
	 * <li>If there is no "a" or "e", look for an "ou". If "ou" appears, then
	 * the "o" takes the tone mark.
	 * 
	 * <li>If none of the above cases hold, then the last vowel in the syllable
	 * takes the tone mark.
	 * 
	 * </ol>
	 * 
	 * @param pinyinStr
	 *            the ascii represention with tone numbers
	 * @return the unicode represention with tone marks
	 */
	private static String convertToneNumber2ToneMark(final String pinyinStr) {
		String lowerCasePinyinStr = pinyinStr.toLowerCase();
		if (lowerCasePinyinStr.matches("[a-z]*[1-5]?")) {
			final char defautlCharValue = '$';
			final int defautlIndexValue = -1;

			char unmarkedVowel = defautlCharValue;
			int indexOfUnmarkedVowel = defautlIndexValue;

			final char charA = 'a';
			final char charE = 'e';
			final String ouStr = "ou";
			final String allUnmarkedVowelStr = "aeiouv";
			final String allMarkedVowelStr = "āáăàaēéĕèeīíĭìiōóŏòoūúŭùuǖǘǚǜü";

			if (lowerCasePinyinStr.matches("[a-z]*[1-5]")) {

				int tuneNumber = Character.getNumericValue(lowerCasePinyinStr
						.charAt(lowerCasePinyinStr.length() - 1));

				int indexOfA = lowerCasePinyinStr.indexOf(charA);
				int indexOfE = lowerCasePinyinStr.indexOf(charE);
				int ouIndex = lowerCasePinyinStr.indexOf(ouStr);

				if (-1 != indexOfA) {
					indexOfUnmarkedVowel = indexOfA;
					unmarkedVowel = charA;
				} else if (-1 != indexOfE) {
					indexOfUnmarkedVowel = indexOfE;
					unmarkedVowel = charE;
				} else if (-1 != ouIndex) {
					indexOfUnmarkedVowel = ouIndex;
					unmarkedVowel = ouStr.charAt(0);
				} else {
					for (int i = lowerCasePinyinStr.length() - 1; i >= 0; i--) {
						if (String.valueOf(lowerCasePinyinStr.charAt(i))
								.matches("[" + allUnmarkedVowelStr + "]")) {
							indexOfUnmarkedVowel = i;
							unmarkedVowel = lowerCasePinyinStr.charAt(i);
							break;
						}
					}
				}

				if ((defautlCharValue != unmarkedVowel)
						&& (defautlIndexValue != indexOfUnmarkedVowel)) {
					int rowIndex = allUnmarkedVowelStr.indexOf(unmarkedVowel);
					int columnIndex = tuneNumber - 1;

					int vowelLocation = rowIndex * 5 + columnIndex;

					char markedVowel = allMarkedVowelStr.charAt(vowelLocation);

					StringBuffer resultBuffer = new StringBuffer();

					resultBuffer.append(lowerCasePinyinStr.substring(0,
							indexOfUnmarkedVowel).replaceAll("v", "ü"));
					resultBuffer.append(markedVowel);
					resultBuffer.append(lowerCasePinyinStr.substring(
							indexOfUnmarkedVowel + 1,
							lowerCasePinyinStr.length() - 1).replaceAll("v",
							"ü"));

					return resultBuffer.toString();

				} else
				// error happens in the procedure of locating vowel
				{
					return lowerCasePinyinStr;
				}
			} else
			// input string has no any tune number
			{
				// only replace v with ü (umlat) character
				return lowerCasePinyinStr.replaceAll("v", "ü");
			}
		} else
		// bad format
		{
			return lowerCasePinyinStr;
		}
	}
}
