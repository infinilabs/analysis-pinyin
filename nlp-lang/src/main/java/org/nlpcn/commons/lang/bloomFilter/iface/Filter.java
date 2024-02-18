package org.nlpcn.commons.lang.bloomFilter.iface;

import org.nlpcn.commons.lang.bloomFilter.bitMap.IntMap;
import org.nlpcn.commons.lang.bloomFilter.bitMap.LongMap;

public abstract class Filter {

	public static enum MACHINENUM {
		X86, X64
	}

	;

	protected BitMap bm = null;

	protected long size = 0;


	public Filter(long maxValue, MACHINENUM X) throws Exception {
		this.size = maxValue;
		if (X == MACHINENUM.X86) {
			bm = new IntMap((int) (size / 32));
		} else if (X == MACHINENUM.X64) {
			bm = new LongMap((int) (size / 64));
		} else {
			throw new Exception("unknow math num!");
		}
	}


	public Filter(long maxValue) throws Exception {
		this.size = maxValue;
		bm = new IntMap((int) (size / 32));
	}

	/**
	 * @创建人：Ansj -创建时间：2011-9-8 下午03:23:07
	 * @方法描述： @param str
	 * @方法描述： @return 判断一个字符串是否bitMap中存在
	 */
	public boolean contains(String str) {
		long hash = this.myHashCode(str);
		return bm.contains(hash);
	}

	/**
	 * @创建人：Ansj -创建时间：2011-9-8 下午03:22:42
	 * @方法描述： @param str 在boolean的bitMap中增加一个字符串
	 */
	public void add(String str) {
		long hash = this.myHashCode(str);
		bm.add(hash);
	}


	/**
	 * @创建人：Ansj -创建时间：2011-9-8 下午03:21:51
	 * @方法描述： @param str 需要增加并且查询的方法
	 * @方法描述： @return 如果存在就返回true .如果不存在.先增加这个字符串.再返回false
	 */
	public boolean containsAndAdd(String str) {
		long hash = this.myHashCode(str);
		if (bm.contains(hash)) {
			return true;
		} else {
			bm.add(hash);
		}
		return false;
	}

	/**
	 * @创建人：Ansj -创建时间：2011-9-8 下午04:36:21
	 * @方法描述： @param chars 传入char数组
	 * @方法描述： @return
	 */
	public abstract long myHashCode(String str);

}
