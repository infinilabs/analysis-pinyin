package org.nlpcn.commons.lang.util;

import org.junit.Assert;
import org.junit.Test;


import java.io.File;

public class FileFinderTest {

	@Test
	public void test() {

		File find = (FileFinder.findByFile(new File("./"), "FileFinder.java",10));
		
		Assert.assertNotNull(find);
		
		find = (FileFinder.findByFile(new File("./"), "FileFinder.java",9));
		
		Assert.assertNull(find);
	}

}
