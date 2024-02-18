package org.nlpcn.commons.lang.bloomFilter.bitMap;


import org.nlpcn.commons.lang.bloomFilter.iface.BitMap;

/**
 * 
* @��Ŀ��ƣ�Test   
* @����ƣ�LongMap   
* @��������   ������BitMap��32λ������.������ܷ����õ�Ч��.һ������½���ʹ�ô���
* @�����ˣ�Ansj   
* @����ʱ�䣺2011-9-8 ����03:17:20  
* @�޸ı�ע��   
* @version    
*
 */
public class IntMap implements BitMap {

	private static final int MAX = Integer.MAX_VALUE;


	public IntMap() {
		ints = new int[93750000];
	}

	public IntMap(int size) {
		ints = new int[size];
	}

	private int[] ints = null;

	public void add(long i) {
		int r = (int) (i / 32);
		int c = (int) (i % 32);
		ints[r] = (int) (ints[r] | (1 << c));
	}

	public boolean contains(long i) {
		int r = (int) (i / 32);
		int c = (int) (i % 32);
		if (((int) ((ints[r] >>> c)) & 1) == 1) {
			return true;
		}
		return false;
	}

	public void remove(long i) {
		int r = (int) (i / 32);
		int c = (int) (i % 32);
		ints[r] = (int) (ints[r] & (((1 << (c + 1)) - 1) ^ MAX));
	}

}
