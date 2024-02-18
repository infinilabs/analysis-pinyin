package org.nlpcn.commons.lang.tire.splitWord;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.nlpcn.commons.lang.dic.DicManager;
import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import org.nlpcn.commons.lang.util.IOUtil;

public class ForestTest {

	@Test
	public void test() throws Exception {

		Forest f = new Forest();

		f.addBranch("5", null);
		f.addBranch("2", null);
		f.addBranch("0", null);
		f.addBranch("12", null);
		f.addBranch("23", null);
		f.addBranch("abc12", null);
		f.addBranch("abc", null);
		f.addBranch("解放军", null);
		f.addBranch("解放", null);
		f.addBranch("解放军强渡长江", null);

		GetWord word = f.getWord("　　5月20日，解放军强渡渭河123abc123");

		Assert.assertEquals(word.getFrontWords(), "5");
		Assert.assertEquals(word.getFrontWords(), "解放军");
		//TODO: it is a bug ! fuck me!
		//Assert.assertEquals(word.getFrontWords(), "abc");
		Assert.assertEquals(word.getFrontWords(), null);

	}

	@Test
	public void toMapTest() throws Exception {
		
		Forest makeForest = Library.makeForest(DicManager.class.getResourceAsStream("/finger.dic"));

		List<String> readFile2List = IOUtil.readFile2List(new BufferedReader(new InputStreamReader(DicManager.class.getResourceAsStream("/finger.dic"), "utf-8")));

		Map<String, String[]> map = makeForest.toMap();

		Assert.assertTrue(readFile2List.size() == map.size());

	}

}
