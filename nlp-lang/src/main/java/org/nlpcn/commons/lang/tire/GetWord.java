package org.nlpcn.commons.lang.tire;

import org.nlpcn.commons.lang.tire.domain.Forest;

/**
 * 基本的string【】 类
 * 
 * @author ansj
 *
 */
public class GetWord extends SmartGetWord<String[]> {

	public GetWord(Forest forest, char[] chars) {
		super(forest, chars);
	}

	public GetWord(Forest forest, String content) {
		super(forest, content);
	}

	public String getParam(int i) {
		final String[] param = this.getParam();
		if (param == null || i >= param.length) {
			return null;
		} else {
			return param[i];
		}
	}

	public String[] getParams() {
		return this.getParam();
	}

}
