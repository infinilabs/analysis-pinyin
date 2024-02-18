package org.nlpcn.commons.lang.bloomFilter.filter;


import org.nlpcn.commons.lang.bloomFilter.iface.Filter;

public class RSFilter extends Filter {

	public RSFilter(long maxValue) throws Exception {
		super(maxValue);
	}

	public RSFilter(long maxValue, MACHINENUM X) throws Exception {
		super(maxValue, X);
	}

	@Override
	public long myHashCode(String str) {
		int b = 378551;
		int a = 63689;
		int hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = hash * a + str.charAt(i);
			a = a * b;
		}
		return hash % size;
	}

}
