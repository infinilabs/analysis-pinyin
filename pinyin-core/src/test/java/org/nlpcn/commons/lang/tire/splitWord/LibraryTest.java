package org.nlpcn.commons.lang.tire.splitWord;

import org.junit.Assert;
import org.junit.Test;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;


public class LibraryTest {

	@Test
	public void test() throws Exception {
		Forest makeForest = Library.makeForest("src/test/resources/library.txt","utf-8") ;
		
		Assert.assertNotNull(makeForest.getBranch("上访"));
	}

}
