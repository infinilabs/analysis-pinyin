package org.nlpcn.commons.lang.index;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.nlpcn.commons.lang.pinyin.Pinyin;
import org.nlpcn.commons.lang.util.StringUtil;

public class MemoryIndex<T> {

	private Map<String, TreeSet<Entry>> index = new HashMap<String, TreeSet<Entry>>();

	private int size;

	private Model model;

	public enum Model {
		ALL, PREX
	}

	/**
	 * 倒排hash配置
	 * 
	 * @param size
	 *            返回的条数
	 * @param model
	 *            前缀 或者全索引
	 */
	public MemoryIndex(final int size, final Model model) {
		this.size = size;
		this.model = model;
	}

	public MemoryIndex() {
		this.size = 10;
		this.model = Model.ALL;
	}

	/**
	 * 搜索提示
	 * 
	 * @param value
	 *            返回内容
	 * @param score
	 *            分数
	 * @param fields
	 *            提示内容
	 */
	public void addItem(T value, Double score, String... fields) {
		Set<String> result = null;

		if (fields == null || fields.length == 0) {
			fields = new String[] { value.toString() };
		}

		switch (model) {
		case ALL:
			result = getAllSplit(fields);
			break;
		case PREX:
			result = getPrexSplit(fields);
			break;
		}

		TreeSet<Entry> treeSet;
		for (String key : result) {
			if (StringUtil.isBlank(key)) {
				continue;
			}
			treeSet = index.get(key);

			if (treeSet == null) {
				treeSet = new TreeSet<Entry>();
				index.put(key, treeSet);
			}
			treeSet.add(new Entry(value, score(value, score)));

			if (treeSet.size() > this.size) {
				treeSet.pollLast();
			}
		}
	}

	public void addItem(T value, String... fields) {
		addItem(value, null, fields);
	}

	private Set<String> getAllSplit(final String[] fields) {
		HashSet<String> hs = new HashSet<String>();
		for (String string : fields) {
			if (StringUtil.isBlank(string)) {
				continue;
			}
			string = string.trim();
			for (int i = 0; i < string.length(); i++) {
				for (int j = i + 1; j < string.length() + 1; j++) {
					hs.add(string.substring(i, j));
				}
			}
		}
		return hs;
	}

	private Set<String> getPrexSplit(final String[] fields) {
		HashSet<String> hs = new HashSet<String>();
		for (String string : fields) {
			if (StringUtil.isBlank(string)) {
				continue;
			}

			string = string.trim();

			for (int i = 1; i < string.length() + 1; i++) {
				hs.add(string.substring(0, i));
			}
		}

		return hs;
	}

	public double score(final T value, final Double score) {
		if (score != null) {
			return score;
		}
		double weight = 0;
		if (value instanceof String) {
			weight = Math.log(Math.E / (double) value.toString().length());
		}
		return weight;
	}

	public class Entry implements Comparable<Entry> {
		private double score;
		private T t;

		public Entry(final T t, final Double score) {
			this.t = t;
			this.score = score;
		}

		@Override
		public int compareTo(final Entry o) {
			if (this.t.equals(o.t)) {
				return 0;
			}

			if (this.score > o.score) {
				return -1;
			} else {
				return 1;
			}
		}

		@Override
		public boolean equals(Object o) {
			if ((o instanceof MemoryIndex.Entry)) {
				@SuppressWarnings("all")
				Entry e = (MemoryIndex.Entry) o;
				return e.t.equals(this.t);
			}
			return false;
		}

		public double getScore() {
			return score;
		}

		public T getValue() {
			return t;
		}

		@Override
		public String toString() {
			return t.toString();
		}

	}

	/**
	 * 将字符串转换为全拼
	 * 
	 * @param str
	 * @return
	 */
	public String str2QP(final String str) {
		return Pinyin.list2String(Pinyin.pinyin(str),"");
	}

	public List<T> suggest(String key) {
		if (StringUtil.isBlank(key)) {
			return Collections.emptyList();
		}

		key = key.replace("\\s", "");

		List<T> result = new LinkedList<T>();
		TreeSet<Entry> treeSet = index.get(key);
		if (treeSet == null) {
			return result;
		}

		for (Entry entry : treeSet) {
			result.add(entry.t);
		}
		return result;
	}

	/**
	 * 类似模糊查询。支持错别字，
	 * 
	 * @param key
	 * @return
	 */
	public List<T> smartSuggest(String key) {
		if (StringUtil.isBlank(key)) {
			return Collections.emptyList();
		}

		key = key.replace("\\s", "");

		List<T> result = suggest(key);

		Set<T> sets = new HashSet<T>();
		sets.addAll(result);

		if (result.size() < size) {
			// 尝试全拼
			List<T> suggest = suggest(str2QP(key));
			for (T t : suggest) {
				if (sets.contains(t)) {
					continue;
				} else {
					sets.add(t);
					result.add(t);
				}
			}
		}

		sets.addAll(result);

		if (result.size() < size) {
			// 尝试首字母拼音
			List<T> suggest = suggest(Pinyin.list2String(Pinyin.firstChar(key),""));
			for (T t : suggest) {
				if (sets.contains(t)) {
					continue;
				} else {
					sets.add(t);
					result.add(t);
				}
			}

		}

		if (result.size() <= size) {
			return result;
		}

		return result.subList(0, size);
	}
}
