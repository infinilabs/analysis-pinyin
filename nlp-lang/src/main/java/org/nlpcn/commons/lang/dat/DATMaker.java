package org.nlpcn.commons.lang.dat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.nlpcn.commons.lang.tire.domain.SmartForest;
import org.nlpcn.commons.lang.util.FileIterator;
import org.nlpcn.commons.lang.util.IOUtil;
import org.nlpcn.commons.lang.util.StringUtil;

/**
 * dat maker
 *
 * @author ansj
 */
public class DATMaker {

	private static final Logger LOG = Logger.getLogger(DATMaker.class.getName());

	// 动态数组扩容
	private final int capacity = 100000;
	private Item[] dat = new Item[capacity];

	/**
	 * 构建默认的DAT
	 */
	public void maker(final String dicPath) throws Exception {
		maker(dicPath, BasicItem.class);
	}

	/**
	 * 构建用户自定义的dat
	 * 
	 * @throws FileNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public void maker(final String dicPath, final Class<? extends Item> cla) throws FileNotFoundException, InstantiationException, IllegalAccessException {
		long start = System.currentTimeMillis();
		LOG.info("make basic tire begin !");

		final SmartForest<Item> forest = new SmartForest<Item>();
		final FileIterator it = IOUtil.instanceFileIterator(dicPath, IOUtil.UTF8);
		if (it == null) {
			throw new FileNotFoundException();
		}
		try {
			String temp;
			while (it.hasNext()) {
				temp = it.next();
				if (StringUtil.isBlank(temp)) {
					continue;
				}
				final Item item = cla.newInstance();
				final String[] split = temp.split("\t");
				item.init(split);
				forest.add(split[0], item);
			}
		} finally {
			it.close();
		}
		LOG.info("make basic tire over use time " + (System.currentTimeMillis() - start) + " ms");

		start = System.currentTimeMillis();
		LOG.info("make dat tire begin !");
		makeDAT(tree2List(cla, forest));
		LOG.info("make dat tire over use time " + (System.currentTimeMillis() - start) + " ms! dat len is " + datArrLen() + "! dat size is " + datItemSize());

	}

	private void makeDAT(final List<Item> all) {
		// all 就是tire树中没一个前缀集合
		for (int i = 0; i < all.size(); i++) {
			final Item item = all.get(i);
			final char[] chars = item.name.toCharArray();// 每个节点中的词.
			final int length = chars.length;
			// 如果词长度为一.直接放置到ascii码的位置上.并且保证此字的值大于65536
			if (length == 1) {
				item.check = -1;
				item.index = chars[0];
				dat[item.index] = item;
			} else {
				// 得道除了尾字啊外的位置,比如 "中国人" 此时返回的是"中国"的Item
				// 前缀是否相同,如果相同保存在临时map中.直到不同
				final Item pre = getPre(item);// 用来保留默认前缀
				final List<Item> group = findGroup(all, i, pre);
				item2DAT(pre, group);
				i = i + group.size() - 1;
			}
		}
	}

	/**
	 * 找到相同的组
	 */
	private List<Item> findGroup(final List<Item> all, final int i, final Item pre) {
		final List<Item> group = new ArrayList<Item>();
		group.add(all.get(i));
		for (int j = i + 1; j < all.size(); j++) {
			final Item tmp = all.get(j);
			if (pre == getPre(tmp)) {
				group.add(tmp);
			} else {
				break;
			}
		}
		return group;
	}

	/**
	 * 将迭代结果存入dat
	 */
	private void item2DAT(final Item pre, final List<Item> samePreGroup) {
		updateBaseValue(samePreGroup, pre);
		// 处理完冲突后将这些值填充到双数组中
		for (final Item tmp : samePreGroup) {
			tmp.index = pre.base + getLastChar(tmp.name);
			dat[pre.base + getLastChar(tmp.name)] = tmp;
			tmp.check = pre.index;
		}
	}

	/**
	 * 更新上一级的base值，直到冲突解决
	 */
	private void updateBaseValue(final List<Item> samePreGroup, final Item pre) {
		Iterator<Item> iterator = samePreGroup.iterator();
		while (iterator.hasNext()) {
			final Item item = iterator.next();

			checkLength(pre.base + getLastChar(item.name));

			if (dat[pre.base + getLastChar(item.name)] != null) {
				pre.base++;
				iterator = samePreGroup.iterator();
			}
		}
	}

	/**
	 * 检查数组长度并且扩容
	 */
	private void checkLength(final int len) {
		if (len >= dat.length) {
			dat = Arrays.copyOf(dat, len + capacity);
		}
	}

	/**
	 * 获得一个词语的最后一个字符
	 */
	public char getLastChar(final String word) {
		return word.charAt(word.length() - 1);
	}

	/**
	 * 找到该字符串上一个的位置字符串上一个的位置
	 *
	 * @param item
	 *            传入的字符串char数组
	 */
	public Item getPre(final Item item) {
		final char[] chars = item.name.toCharArray();
		int tempBase = 0;
		if (chars.length == 2) {
			return dat[chars[0]];
		}
		for (int i = 0; i < chars.length - 1; i++) {
			if (i == 0) {
				tempBase += chars[i];
			} else {
				tempBase = dat[tempBase].base + chars[i];
			}
		}
		return dat[tempBase];
	}

	/**
	 * 将tire树 广度遍历为List
	 * 
	 * @throws InstantiationException
	 */
	private List<Item> tree2List(final Class<? extends Item> cla, final SmartForest<Item> forest) throws InstantiationException, IllegalAccessException {
		final List<Item> all = new ArrayList<Item>();
		treeToLibrary(cla, all, forest, "");
		return all;
	}

	/**
	 * 广度遍历
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private void treeToLibrary(final Class<? extends Item> cla, final List<Item> all, final SmartForest<Item> sf, final String preStr)
			throws InstantiationException, IllegalAccessException {
		final SmartForest<Item>[] branches = sf.getBranches();
		if (branches == null) {
			return;
		}

		for (final SmartForest<Item> branche : branches) {
			if (branche == null) {
				continue;
			}
			// 将branch的状态赋予
			Item param = branche.getParam();
			if (param == null) {
				param = cla.newInstance();
				param.name = preStr + (branche.getC());
				param.status = 1;
			} else {
				param.status = branche.getStatus();
			}
			all.add(param);
		}

		for (final SmartForest<Item> branche : branches) {
			if (branche != null) {
				treeToLibrary(cla, all, branche, preStr + (branche.getC()));
			}
		}
	}

	/**
	 * 取得数组的真实长度
	 */
	private int datArrLen() {
		for (int i = this.dat.length - 1; i >= 0; i--) {
			if (this.dat[i] != null) {
				return i + 1;
			}
		}
		return 0;
	}

	/**
	 * 取得数组的真实长度
	 */
	private int datItemSize() {
		int size = 0;
		for (int i = this.dat.length - 1; i >= 0; i--) {
			if (this.dat[i] != null) {
				size++;
			}
		}
		return size;
	}

	/**
	 * 获得dat数组
	 */
	public Item[] getDAT() {
		return this.dat;
	}

	/**
	 * 序列化dat对象
	 */
	public void save(final String path) throws IOException {
		ObjectOutput writer = null;
		try {
			writer = new ObjectOutputStream(new FileOutputStream(path));
			writer.writeInt(datArrLen());
			writer.writeInt(datItemSize());
			for (Item item : dat) {
				if (item == null) {
					continue;
				}
				writer.writeObject(item);
			}
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

	/**
	 * 保存到可阅读的文本.需要重写这个类
	 * 
	 * @throws IOException
	 */
	public void saveText(final String path) throws IOException {
		Writer writer = null;
		try {
			writer = new FileWriter(new File(path));
			writer.write(String.valueOf(datArrLen()));
			writer.write('\n');
			for (final Item item : dat) {
				if (item != null) {
					writer.write(item.toString());
					writer.write('\n');
				}
			}
			writer.flush();
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}
}