package org.nlpcn.commons.lang.finger;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nlpcn.commons.lang.dic.DicManager;
import org.nlpcn.commons.lang.finger.SimHashService.Index;
import org.nlpcn.commons.lang.util.IOUtil;
import org.nlpcn.commons.lang.util.MurmurHash;

public class SimHashServiceTest {

	private SimHashService simHashService;

	private List<String> readFile2List;

	@Before
	public void init() throws UnsupportedEncodingException {
		simHashService = new SimHashService();
		readFile2List = IOUtil.readFile2List(DicManager.class.getResourceAsStream("/finger.dic"), "utf-8");
	}

	@Test
	public void fingerPrintTest() throws UnsupportedEncodingException {

		for (String string : readFile2List) {

			string = string.split("\t")[0];

			long hashCode = MurmurHash.hash64(string);

			long fingerprint = simHashService.fingerprint(string);

			Assert.assertEquals(fingerprint, hashCode);

		}
	}

	@Test
	public void hmDistanceTest() {

		String content = "卓尔防线继续伤筋动骨 队长梅方出场再补漏说起来卓尔队长梅方本赛季就是个“补漏”的命！在中卫与右边后卫间不停地轮换。如果不出意外，今天与广州恒大一战梅方又要换位置，这也是汉军队长连续三场比赛中的第三次换位。而从梅方的身上也可以看出，本赛季汉军防线如此“折腾”，丢球多也不奇怪了。梅方自2009赛季中乙出道便一直司职中后卫，还曾入选过布拉泽维奇国奥队，也是司职的中卫。上赛季，梅方与忻峰搭档双中卫帮助武汉卓尔队中超成功，但谁知进入本赛季后从第一场比赛开始梅方便不断因为种种“意外”而居无定所。联赛首战江苏舜天时，也是由于登贝莱受伤，朱挺位置前移，梅方临危受命客串右边后卫。第二轮主场与北京国安之战梅方仅仅打了一场中卫，又因为柯钊受罚停赛4轮而不得不再次到边路“补漏”。随着马丁诺维奇被弃用，梅方一度成为中卫首选，在与上海东亚队比赛中，邱添一停赛，梅方与忻峰再度携手，紧接着与申鑫队比赛中移至边路，本轮忻峰又停赛，梅方和邱添一成为中卫线上最后的选择。至于左右边后卫位置，卓尔队方面人选较多，罗毅、周恒、刘尚坤等人均可出战。记者马万勇原标题：卓尔防线继续伤筋动骨队长梅方出场再补漏稿源：中新网作者：";
		String content2 = "卓尔防线继续伤筋动骨 队长梅方出场再补漏说起来卓尔队长梅方本赛季就是个“补漏”的命！在中卫与右边后卫间不停地轮换。如果不出意外，今天与广州恒大一战梅方又要换位置，这也是汉军队长连续三场比赛中的第三次换位。而从梅方的身上也可以看出，本赛季汉军防线如此“折腾”，丢球多也不奇怪了。梅方自2009赛季中乙出道便一直司职中后卫，还曾入选过布拉泽维奇国奥队，也是司职的中卫。上赛季，梅方与忻峰搭档双中卫帮助武汉卓尔队中超成功，但谁知进入本赛季后从第一场比赛开始梅方便不断因为种种“意外”而居无定所。联赛首战江苏舜天时，也是由于登贝莱受伤，朱挺位置前移，梅方临危受命客串右边后卫。第二轮主场与北京国安之战梅方仅仅打了一场中卫，又因为柯钊受罚停赛4轮而不得不再次到边路“补漏”。随着马丁诺维奇被弃用，梅方一度成为中卫首选，在与上海东亚队比赛中，邱添一停赛，梅方与忻峰再度携手，紧接着与申鑫队比赛中移至边路，本轮忻峰又停赛，梅方和邱添一成为中卫线上最后的选择。至于左右边后卫位置，卓尔队方面人选较多，罗毅、周恒、刘尚坤等人均可出战。记者马万勇";
		Assert.assertTrue(simHashService.hmDistance(content, content2) < 7);
	}

	@Test
	public void indexTest() throws UnsupportedEncodingException, FileNotFoundException {

		Index index = simHashService.createIndex();

		List<String> lists = IOUtil.readFile2List(DicManager.class.getResourceAsStream("/test.json"), "utf-8");

		long start = System.currentTimeMillis();
		int m = 0;
		for (String string : lists) {
			int nearest = index.nearest(string);
			if (nearest < 7) {
				m++;
			} else {
				index.add(string);
			}
		}
		Assert.assertTrue(m > 300 && m < 500);
		System.out.println(System.currentTimeMillis() - start);

	}

	
//	@Test
//	public void dicIndexTest() throws UnsupportedEncodingException, FileNotFoundException {
//
//		Index index = simHashService.createIndex();
//
//		List<String> lists = IOUtil.readFile2List(DicManager.class.getResourceAsStream("/finger.dic"), "utf-8");
//
//		long start = System.currentTimeMillis();
//		int m = 0;
//int k =0 ;		
//		for (String string : lists) {
//			
//			int nearest = index.nearest(string);
//System.out.println((k++)+"\t"+nearest);			
//			if (nearest < 7) {
//				m++;
//			} else {
//				index.add(string);
//			}
//		}
//		Assert.assertTrue(m > 300 && m < 500);
//		System.out.println(System.currentTimeMillis() - start);
//
//	}
	@Test
	public void distanceTest() throws UnsupportedEncodingException, FileNotFoundException {

		List<String> lists = IOUtil.readFile2List(DicManager.class.getResourceAsStream("/test.json"), "utf-8");


		long start = System.currentTimeMillis();
		for (String str1 : lists.subList(0, 1)) {
			for (String str2 : lists) {

				if (str1.equals(str2)) {
					continue;
				}

				int d = 0;
				if ((d = simHashService.hmDistance(str1, str2)) < 7) {
					System.out.println("----------" + d + "----------");
					System.out.println(str1);
					System.out.println(str2);
				}

			}
		}

		System.out.println(System.currentTimeMillis() - start);
	}

}
