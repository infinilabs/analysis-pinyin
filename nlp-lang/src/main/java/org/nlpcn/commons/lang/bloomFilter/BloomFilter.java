package org.nlpcn.commons.lang.bloomFilter;

import org.nlpcn.commons.lang.bloomFilter.filter.JSFilter;
import org.nlpcn.commons.lang.bloomFilter.filter.JavaFilter;
import org.nlpcn.commons.lang.bloomFilter.filter.PHPFilter;
import org.nlpcn.commons.lang.bloomFilter.filter.PJWFilter;
import org.nlpcn.commons.lang.bloomFilter.filter.SDBMFilter;
import org.nlpcn.commons.lang.bloomFilter.iface.Filter;

/**
 * BlommFilter 实现 1.构建hash算法 2.散列hash映射到数组的bit位置 3.验证
 * 
 * @author Ansj
 */
public class BloomFilter {

	private static int length = 5;

	Filter[] filters = new Filter[length];

	public BloomFilter(int m) throws Exception {
		float mNum = m / 5;
		long size = (long) (1L * mNum * 1024 * 1024 * 8);
		filters[0] = new JavaFilter(size);
		filters[1] = new PHPFilter(size);
		filters[2] = new JSFilter(size);
		filters[3] = new PJWFilter(size);
		filters[4] = new SDBMFilter(size);
	}

	public void add(String str) {
		for (int i = 0; i < length; i++) {
			filters[i].add(str);
		}
	}

	public boolean contains(String str) {
		for (int i = 0; i < length; i++) {
			if (!filters[i].contains(str)) {
				return false;
			}
		}
		return true;
	}

	public boolean containsAndAdd(String str) {
		boolean flag = this.contains(str) ;
		if(!flag){
			this.add(str);
		}
		return flag ;
	}

}
