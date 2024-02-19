package org.nlpcn.commons.lang.util;

import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Test;

public class WordWeightTest {

	private WordWeight ww = new WordWeight();

	@Test
	public void exportTest() {
		ww.add("a");
		ww.add("a");
		ww.add("b");
		ww.add("b");
		ww.add("b");
		ww.add("c");
		System.out.println(ww.export());
	}

	@Test
	public void exportIDFTest() {
		ww.add("a");
		ww.add("a");
		ww.add("b");
		ww.add("b");
		ww.add("b");
		ww.add("c");
		System.out.println(ww.exportIDF());
	}

	@Test
	public void exportChiSquareTest() {
		ww.add("a", "t1");
		ww.add("a", "t2");
		ww.add("b", "t1");
		ww.add("b", "t1");
		ww.add("b", "t2");
		ww.add("c", "t2");
		HashMap<String, MapCount<String>> exportChiSquare = ww.exportChiSquare();
		for (Entry<String, MapCount<String>> entry : exportChiSquare.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue().get());
		}
	}

	@Test
	public void recyclingTest() {
		ww = new WordWeight(5, 3);
		ww.add("a", "t1");
		ww.add("a", "t2");
		ww.add("b", "t1");
		ww.add("b", "t1");
		ww.add("b", "t2");
		ww.add("c", "t2");
		ww.add("d", "t2");
		ww.add("e", "t2");
		ww.add("f", "t2");
		ww.add("f", "t2");
		System.out.println(ww.export());
		System.out.println(ww.exportIDF());
		HashMap<String, MapCount<String>> exportChiSquare = ww.exportChiSquare();
		for (Entry<String, MapCount<String>> entry : exportChiSquare.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue().get());
		}

	}

}
