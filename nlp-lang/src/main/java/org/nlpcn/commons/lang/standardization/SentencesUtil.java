package org.nlpcn.commons.lang.standardization;

import java.util.ArrayList;
import java.util.List;

/**
 * 文本断句
 * 
 * @author ansj
 * 
 */
public class SentencesUtil {
	public List<String> toSentenceList(String content) {
		return toSentenceList(content.toCharArray());
	}

	public List<String> toSentenceList(char[] chars) {

		StringBuilder sb = new StringBuilder();

		List<String> sentences = new ArrayList<String>();

		for (int i = 0; i < chars.length; i++) {
			if (sb.length() == 0 && (Character.isWhitespace(chars[i]) || chars[i] == ' ')) {
				continue;
			}

			sb.append(chars[i]);
			switch (chars[i]) {
			case '.':
				if (i < chars.length - 1 && chars[i + 1] > 128) {
					insertIntoList(sb, sentences);
					sb = new StringBuilder();
				}
				break;
			case '…':
				insertIntoList(sb, sentences);
				sb = new StringBuilder("…");
				break;
			case '\t':
			case '。':
			case ';':
			case '；':
			case '!':
			case '！':
			case '?':
			case '？':
			case '\n':
			case '\r':
				insertIntoList(sb, sentences);
				sb = new StringBuilder();
				break;
			}
		}

		if (sb.length() > 0) {
			insertIntoList(sb, sentences);
		}

		return sentences;
	}

	private void insertIntoList(StringBuilder sb, List<String> sentences) {
		String content = sb.toString().trim();
		if (content.length() > 0) {
			sentences.add(content);
		}
	}

}
