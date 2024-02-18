package org.nlpcn.commons.lang.bloomFilter.filter;


import org.nlpcn.commons.lang.bloomFilter.iface.Filter;

public class PJWFilter extends Filter {

	public PJWFilter(long maxValue) throws Exception {
		super(maxValue);
	}

	public PJWFilter(long maxValue, MACHINENUM X) throws Exception {
		super(maxValue, X);
	}

	@Override
	public long myHashCode(String str) {
		// TODO Auto-generated method stub
		int BitsInUnsignedInt = 32;
		int ThreeQuarters = (BitsInUnsignedInt * 3) / 4;
		int OneEighth = BitsInUnsignedInt / 8;
		int HighBits = 0xFFFFFFFF << (BitsInUnsignedInt - OneEighth);
		int hash = 0;
		int test = 0;

		for (int i = 0; i < str.length(); i++) {
			hash = (hash << OneEighth) + str.charAt(i);

			if ((test = hash & HighBits) != 0) {
				hash = ((hash ^ (test >> ThreeQuarters)) & (~HighBits));
			}
		}

		return hash%size;
	}

}
