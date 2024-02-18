package org.nlpcn.commons.lang.dat;

import static org.nlpcn.commons.lang.TestUtils.testResources;

import java.io.File;

import org.junit.Test;

public class DATTest {

	@Test
	public void testMakeSaveAndLoad() {
		try {
			DATMaker dat = new DATMaker();
			dat.maker(testResources("library.txt"));

			dat.saveText("dat.txt");
			long start = System.currentTimeMillis();
			DoubleArrayTire load = DoubleArrayTire.loadText("dat.txt");
			System.out.println("" + load.getItem("中"));
			System.out.println("load obj use time " + (System.currentTimeMillis() - start));

			dat.save("dat.obj");
			start = System.currentTimeMillis();
			load = DoubleArrayTire.load("dat.obj");
			System.out.println("" + load.getItem("中国"));
			System.out.println("load obj use time " + (System.currentTimeMillis() - start));
			
			Thread.sleep(3000L);

			new File("dat.txt").deleteOnExit();
			new File("dat.obj").deleteOnExit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
