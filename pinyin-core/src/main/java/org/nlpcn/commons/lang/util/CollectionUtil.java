package org.nlpcn.commons.lang.util;

import java.util.*;

public class CollectionUtil {
	/**
	 * map 按照value排序
	 *
	 * @return
	 */
	public static <K, V> List<Map.Entry<K, V>> sortMapByValue(Map<K, V> map, final int sort) {
		List<Map.Entry<K, V>> orderList = new ArrayList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(orderList, new Comparator<Map.Entry<K, V>>() {
			@Override
			@SuppressWarnings("unchecked")
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (((Comparable<V>) o2.getValue()).compareTo(o1.getValue())) * sort;
			}
		});
		return orderList;
	}

	public static <K, V> Map<K, V> as(K k1, V v1) {
		Map<K,V> result = new HashMap<K, V>() ;
		result.put(k1, v1) ;
		return result ;
	}
	
	
	
}
