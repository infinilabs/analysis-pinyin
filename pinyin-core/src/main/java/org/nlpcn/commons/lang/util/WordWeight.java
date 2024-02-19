package org.nlpcn.commons.lang.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 计算词语的权重,词频统计等
 * 
 * @author ansj
 *
 */
public class WordWeight {

	private MapCount<String> mc = new MapCount<String>(); // 词频统计

	private HashMap<String, MapCount<String>> x2mat = new HashMap<String, MapCount<String>>();

	private MapCount<String> x2mc = new MapCount<String>();

	private Integer maxCount;

	private Integer recyclingCount;

	private double allFreq;

	public WordWeight() {
	};

	/**
	 * 新的个数 = maxCount - recyclingCount; recyclingCount< maxCount
	 * 
	 * @param maxCount
	 *            最大值,当超过这个值后进行回收
	 * @param recyclingCount
	 *            回收个数
	 */
	public WordWeight(Integer maxCount, Integer recyclingCount) {
		this.maxCount = maxCount;
		this.recyclingCount = recyclingCount;
	}

	public void add(String word) {
		add(word, 1);
	}

	public void add(String word, double weight) {
		allFreq += weight;
		mc.add(word, weight);
		if (maxCount != null && recyclingCount != null && mc.get().size() >= maxCount) {
			recycling();
		}
	}

	public void add(String word, String target) {
		add(word, target, 1);
	}

	public void add(String word, String target, double weight) {
		if (x2mat.containsKey(target)) {
			x2mat.get(target).add(word, weight);
		} else {
			x2mat.put(target, new MapCount<String>());
			x2mat.get(target).add(word, weight);
		}
		x2mc.add(target, 1);
		add(word, weight);
	}

	/**
	 * 导出词频统计结果
	 * 
	 * @return
	 */
	public Map<String, Double> export() {
		Map<String, Double> result = new HashMap<String, Double>();
		result.putAll(mc.get());
		return result;
	}

	/**
	 * 导出IDF统计结果
	 * 
	 * @return
	 */
	public Map<String, Double> exportIDF() {

		Map<String, Double> result = new HashMap<String, Double>();

		for (Entry<String, Double> entry : mc.get().entrySet()) {
			result.put(entry.getKey(), Math.log(allFreq / entry.getValue()));
		}

		return result;
	}

	public HashMap<String, MapCount<String>> exportChiSquare() {

		HashMap<String, MapCount<String>> x2final = new HashMap<String, MapCount<String>>();

		double sum = allFreq;

		Double a, b, c, d;

		for (Entry<String, MapCount<String>> iter1 : x2mat.entrySet()) {
			String target = iter1.getKey();
			for (Entry<String, Double> iter2 : iter1.getValue().get().entrySet()) {
				String name = iter2.getKey();
				a = iter2.getValue();
				b = x2mc.get().get(target) - a;
				c = mc.get().get(name) - a;
				d = sum - b - c + a;
				Double x2stat = Math.pow(a * d - b * c, 2) / (a + c) / (b + d);
				if (x2final.get(target) != null) {
					x2final.get(target).add(name, x2stat);
				} else {
					x2final.put(target, new MapCount<String>());
					x2final.get(target).add(name, x2stat);
				}
			}
		}

		return x2final;

	}

	/**
	 * 回收
	 */
	private void recycling() {
		List<Entry<String, Double>> list = CollectionUtil.sortMapByValue(mc.get(), -1);
		Set<String> targetSet = x2mat.keySet();
		String word;
		for (int i = 0; i < recyclingCount; i++) {
			word = list.get(i).getKey();
			allFreq -= mc.get().remove(word); // 从全局中移除数字
			for (String target : targetSet) {
				Double r2 = x2mat.get(target).get().remove(word);
				if (r2 != null) {
					x2mc.add(target, -r2);
				}
			}
		}
	}

}
