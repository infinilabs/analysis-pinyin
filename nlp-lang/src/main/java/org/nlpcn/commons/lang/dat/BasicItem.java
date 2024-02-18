package org.nlpcn.commons.lang.dat;

public class BasicItem extends Item {

	private static final long serialVersionUID = 1L;

	/**
	 * 从词典中加载如果又特殊需求可重写此构造方法
	 * 
	 * @param split split
	 */
	public void init(final String[] split) {
		name = split[0];
	}

	/**
	 * 从生成的词典中加载。应该和toString方法对应
	 * 
	 * @param split split
	 */
	public void initValue(final String[] split) {
		index = Integer.parseInt(split[0]);
		name = split[1];
		base = Integer.parseInt(split[2]);
		check = Integer.parseInt(split[3]);
		status = Byte.parseByte(split[4]);
	}

	public String toText() {
		return index + "\t" + name + "\t" + base + "\t" + check + "\t" + status;
	}

//	@Override
//	public String toString() {
//		return this.toText();
//	}
}
