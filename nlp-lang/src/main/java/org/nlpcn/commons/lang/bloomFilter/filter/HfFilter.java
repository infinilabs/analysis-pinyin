package org.nlpcn.commons.lang.bloomFilter.filter;


import org.nlpcn.commons.lang.bloomFilter.iface.Filter;

public class HfFilter extends Filter {

	public HfFilter(long maxValue) throws Exception {
		super(maxValue);
	}

	public HfFilter(long maxValue, MACHINENUM X) throws Exception {
		super(maxValue, X);
	}

	@Override
	public long myHashCode(String str) {
		// TODO Auto-generated method stub
		int length = str.length();
		long hash = 0;

		for (int i = 0; i < length; i++)
			hash += str.charAt(i) * 3 * i;

		if (hash < 0)
			hash = -hash;

		return hash % size;
	}

}
