package org.nlpcn.commons.lang.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * map 工具类
 * 
 * @author ansj
 *
 * @param <K>
 * @param <V>
 */
public class MapFactory<K, V> {

	private Map<K, V> map = null;

	private MapFactory() {
	}

	public static <K, V> MapFactory<K, V> hashMap() {
		MapFactory<K, V> mf = new MapFactory<K, V>();
		mf.map = new HashMap<K, V>();
		return mf;
	}

	public static <K, V> MapFactory<K, V> treeMap() {
		MapFactory<K, V> mf = new MapFactory<K, V>();
		mf.map = new TreeMap<K, V>();
		return mf;
	}

	public MapFactory<K, V> a(K k, V v) {
		map.put(k, v);
		return this;
	}

	public Map<K, V> toMap() {
		return map;
	}
}
