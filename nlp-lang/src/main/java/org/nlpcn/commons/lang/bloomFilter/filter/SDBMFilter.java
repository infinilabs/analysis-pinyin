package org.nlpcn.commons.lang.bloomFilter.filter;


import org.nlpcn.commons.lang.bloomFilter.iface.Filter;

public class SDBMFilter extends Filter {
	public SDBMFilter(long maxValue) throws Exception {
		super(maxValue);
	}

	public SDBMFilter(long maxValue, MACHINENUM X) throws Exception {
		super(maxValue, X);
	}

	@Override
	public long myHashCode(String str) {
		// TODO Auto-generated method stub
		int hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
		}

		if (hash < 0)
			hash *= -1;

		return hash % size;
	}

}
