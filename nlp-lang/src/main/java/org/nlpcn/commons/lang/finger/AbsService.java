package org.nlpcn.commons.lang.finger;

import org.nlpcn.commons.lang.dic.DicManager;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;

class AbsService {

	/**
	 * 默认停用词词典
	 */
	protected static Forest forest = null;

	static {
		try {
			forest = Library.makeForest(DicManager.class.getResourceAsStream("/finger.dic"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
