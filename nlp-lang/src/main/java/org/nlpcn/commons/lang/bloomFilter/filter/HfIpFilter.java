package org.nlpcn.commons.lang.bloomFilter.filter;


import org.nlpcn.commons.lang.bloomFilter.iface.Filter;

public class HfIpFilter extends Filter {
	public HfIpFilter(long maxValue) throws Exception {
		super(maxValue);
	}

	public HfIpFilter(long maxValue, MACHINENUM X) throws Exception {
		super(maxValue, X);
	}

	@Override
	public long myHashCode(String str) {
		// TODO Auto-generated method stub
		int length = str.length();
		long hash = 0;
		for (int i = 0; i < length; i++) {
			hash += str.charAt(i % 4) ^ str.charAt(i);
		}
		return hash % size;
	}

}
