package org.nlpcn.commons.lang.bloomFilter.filter;


import org.nlpcn.commons.lang.bloomFilter.iface.Filter;

public class JSFilter extends Filter {

	public JSFilter(long maxValue) throws Exception {
		super(maxValue);
	}

	public JSFilter(long maxValue, MACHINENUM X) throws Exception {
		super(maxValue, X);
	}

	@Override
	public long myHashCode(String str) {
		int hash = 1315423911;

		for (int i = 0; i < str.length(); i++) {
			hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
		}
		
		if(hash<0) hash*=-1 ;
		
		return hash % size;
	}

}
