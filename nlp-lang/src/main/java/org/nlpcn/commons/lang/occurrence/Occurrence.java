package org.nlpcn.commons.lang.occurrence;

import org.nlpcn.commons.lang.util.CollectionUtil;
import org.nlpcn.commons.lang.util.MapCount;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * 词语共现计算工具,愚人节快乐 Created by ansj on 4/1/14.
 */
public class Occurrence implements Serializable {

	private static final long serialVersionUID = 1L;

	private int seqId = 0;

	private Map<String, Count> word2Mc = new HashMap<String, Count>();

	private Map<Integer, String> idWordMap = new HashMap<Integer, String>();

	private MapCount<String> ww2Mc = new MapCount<String>();

	private static final String CONN = "\u0000";

	public void addWords(Collection<String> words) {
		List<Element> all = makeCollection2EList(words);
		add(all);
	}

	private List<Element> makeCollection2EList(Collection<String> words) {
		List<Element> all = new ArrayList<Element>(words.size());
		for (String word : words) {
			all.add(new Element(word));
		}
		return all;
	}

	public void addColRow(Collection<String> rows, Collection<String> cols) {
		Count count = null;

		List<Element> colsList = makeCollection2EList(cols);
		List<Element> rowsList = makeCollection2EList(rows);

		for (Element word : colsList) {
			if ((count = word2Mc.get(word.getName())) != null) {
				count.upScore();
			} else {
				count = new Count(word.getNature(), seqId++);
				word2Mc.put(word.getName(), count);
				idWordMap.put(count.id, word.getName());
			}
		}

		for (Element word : rowsList) {
			if ((count = word2Mc.get(word.getName())) != null) {
				count.upScore();
			} else {
				count = new Count(word.getNature(), seqId++);
				word2Mc.put(word.getName(), count);
				idWordMap.put(count.id, word.getName());
			}
		}

		Element e1 = null;
		Element e2 = null;
		Count count1 = null;
		Count count2 = null;

		for (int i = 0; i < rowsList.size() - 1; i++) {
			e1 = rowsList.get(i);
			count1 = word2Mc.get(e1.getName());
			for (int j = i + 1; j < colsList.size(); j++) {
				e2 = colsList.get(j);
				count2 = word2Mc.get(e2.getName());
				if (count1.id == count2.id) {
					continue;
				}
				ww2Mc.add(e1.getName() + CONN + e2.getName());
				count1.upRelation(count2.id);
			}
		}
	}

	public void add(List<Element> words) {
		Count count = null;
		for (Element word : words) {
			if ((count = word2Mc.get(word.getName())) != null) {
				count.upScore();
			} else {
				count = new Count(word.getNature(), seqId++);
				word2Mc.put(word.getName(), count);
				idWordMap.put(count.id, word.getName());
			}

		}

		Element e1 = null;
		Element e2 = null;
		Count count1 = null;
		Count count2 = null;
		for (int i = 0; i < words.size() - 1; i++) {
			e1 = words.get(i);
			count1 = word2Mc.get(e1.getName());
			for (int j = i + 1; j < words.size(); j++) {
				e2 = words.get(j);
				count2 = word2Mc.get(e2.getName());
				if (count1.id == count2.id) {
					continue;
				}

				if (count1.id < count2.id) {
					ww2Mc.add(e1.getName() + CONN + e2.getName());
				} else {
					ww2Mc.add(e2.getName() + CONN + e2.getName());
				}
				count1.upRelation(count2.id);
				count2.upRelation(count1.id);
			}
		}
	}

	/**
	 * 得到两个词的距离
	 * 
	 * @return
	 */
	public double distance(String word1, String word2) {
		Double distance = null;
		if ((distance = ww2Mc.get().get(word1 + CONN + word2)) != null) {
			return distance;
		}
		if ((distance = ww2Mc.get().get(word2 + CONN + word1)) != null) {
			return distance;
		}
		return 0;
	}

	/**
	 * 得到两个词的距离
	 * 
	 * @return
	 */
	public List<Entry<String, Double>> distance(String word) {
		Count count = word2Mc.get(word);

		if (count == null)
			return null;

		Map<String, Double> map = new HashMap<String, Double>();
		String word2 = null;
		for (Integer id : count.relationSet) {
			word2 = idWordMap.get(id);
			map.put(word2, distance(word, word2) * word2Mc.get(word2).score);
		}
		return CollectionUtil.sortMapByValue(map, 1);
	}
	
	public List<Entry<String, Double>> distanceLogFreq1(String word) {
		Count count = word2Mc.get(word);

		if (count == null)
			return null;

		Map<String, Double> map = new HashMap<String, Double>();
		String word2 = null;
		for (Integer id : count.relationSet) {
			word2 = idWordMap.get(id);
			
			map.put(word2,Math.log(distance(word, word2)+1) *( word2Mc.get(word2).score));
		}
		return CollectionUtil.sortMapByValue(map, 1);
	}
	
	public List<Entry<String, Double>> distanceLogFreq(String word) {
		Count count = word2Mc.get(word);

		if (count == null)
			return null;

		Map<String, Double> map = new HashMap<String, Double>();
		String word2 = null;
		for (Integer id : count.relationSet) {
			word2 = idWordMap.get(id);
			
			map.put(word2,word2Mc.get(word).score* Math.log(distance(word, word2)+1) *( word2Mc.get(word2).score));
		}
		return CollectionUtil.sortMapByValue(map, 1);
	}

	/**
	 * tf/idf 计算分数
	 */
	public void computeTFIDF() {
		int size = word2Mc.size();
		Count count = null;
		for (Entry<String, Count> element : word2Mc.entrySet()) {
			count = element.getValue();
			count.score = Math.log((size + count.score) / count.score);
		}
	}

	/**
	 * 保存模型
	 */
	public void saveModel(String filePath) throws IOException {
		ObjectOutput oot = new ObjectOutputStream(new FileOutputStream(filePath));
		oot.writeObject(this);
		oot.flush();
		oot.close();
	}

	/**
	 * 读取模型
	 */
	public static Occurrence loadModel(String filePath) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(filePath));

			return (Occurrence) ois.readObject();
		} finally {
			if (ois != null) {
				ois.close();
			}
		}
	}

	public Map<String, Count> getWord2Mc() {
		return new HashMap<String, Count>(word2Mc);
	}

}
