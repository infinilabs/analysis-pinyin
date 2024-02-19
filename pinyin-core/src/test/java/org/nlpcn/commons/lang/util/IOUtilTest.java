package org.nlpcn.commons.lang.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class IOUtilTest {
	
	@Test
	public void testWriteList() throws IOException {
		List<String> list = new ArrayList<String>() ;
		for (int i = 0; i < 100; i++) {
			list.add(String.valueOf(i)) ;
		}
		
		IOUtil.writeList(list, "list.tmp", "utf-8");
		
		
		List<String> readFile2List = IOUtil.readFile2List("list.tmp", "utf-8") ;
		
		Assert.assertArrayEquals(list.toArray(), readFile2List.toArray());
		
		new File("list.tmp").delete();
		
	}

}
