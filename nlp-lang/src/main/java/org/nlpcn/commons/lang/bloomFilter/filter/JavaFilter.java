package org.nlpcn.commons.lang.bloomFilter.filter;


import org.nlpcn.commons.lang.bloomFilter.iface.Filter;

public class JavaFilter extends Filter {


	public JavaFilter(long maxValue, MACHINENUM X) throws Exception {
		super(maxValue, X);
	}

	public JavaFilter(long maxValue) throws Exception {
		super(maxValue);
	}

	@Override
	public long myHashCode(String str) {
		// TODO Auto-generated method stub
		long hash = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = 31 * hash + str.charAt(i);
		}

		if (hash < 0) {
			hash *= -1;
		}

		return hash % size;
	}

}
