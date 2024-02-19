package org.nlpcn.commons.lang.util.logging;

import org.junit.Test;

public class NLPLoggerTest {

	@Test
	public void test() {
		Log logger = LogFactory.getLog(NLPLoggerTest.class) ;
		logger.info("info hello nlpcn!");
		logger.warn("warn hello nlpcn!");
		logger.error("error hello nlpcn!");
		logger.debug("debug hello nlpcn!");
		
		
		logger.warn("warn hello nlpcn!",new Exception("ansj"));
		logger.error("error hello nlpcn!",new Exception("ansj"));
		logger.debug("debug hello nlpcn!",new Exception("ansj"));
	}

}
