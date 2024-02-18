package org.nlpcn.commons.lang.finger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.util.MurmurHash;

public class SimHashService extends AbsService {

	private static final int BYTE_LEN = 64;

	private static final long[] BITS = new long[BYTE_LEN];

	static {
		BITS[0] = 1;
		for (int i = 1; i < BITS.length; i++) {
			BITS[i] = BITS[i - 1] * 2;
		}
	}

	/**
	 * 比较 ab 的汉明距离
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public int hmDistance(long a, long b) {
		int d = 0;
		a = a ^ b;
		for (int i = 0; i < BYTE_LEN; i++) {
			if ((a & BITS[i]) != 0) {
				d++;
			}
		}
		return d;
	}

	/**
	 * 传入两个文章进行汉明距离比较
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public int hmDistance(String c1, String c2) {
		return hmDistance(fingerprint(c1), fingerprint(c2));
	}

	/**
	 * 获得simhash的指纹
	 * 
	 * @param content
	 * @return
	 */
	public long fingerprint(String content) {
		int[] values = new int[BYTE_LEN];

		for (String word : analysis(content)) {
			long hashCode = hash(word);
			for (int i = 0; i < BYTE_LEN; i++) {
				if ((hashCode & BITS[i]) != 0) {
					values[BYTE_LEN - 1 - i]++;
				} else {
					values[BYTE_LEN - 1 - i]--;
				}
			}
		}

		long result = 0;

		for (int i = 0; i < BYTE_LEN; i++) {
			if (values[i] > 0) {
				result = result | BITS[BYTE_LEN - 1 - i];
			}
		}

		return result;
	}

	/**
	 * 调用分词器，如果你想用自己的分词器。需要覆盖这个方法
	 * 
	 * @return
	 */
	public List<String> analysis(String content) {

		GetWord word = forest.getWord(content);

		String temp = null;

		List<String> all = new ArrayList<String>();

		while ((temp = word.getFrontWords()) != null) {
			all.add(temp);
		}

		return all;
	}

	/**
	 * hash 方法生成hashcode ， 默认采用murmur64的hash算法，如果需要则覆盖这个方法
	 * 
	 * @param word
	 * @return
	 */
	public long hash(String word) {
		return MurmurHash.hash64(word);
	}

	public Index createIndex() {
		return new Index();
	}

	public class Index {

		@SuppressWarnings("unchecked")
		List<Long>[] lists = new List[2048];

		private Index() {
		};

		/**
		 * 增加hashcode到索引中
		 * 
		 * @param <T>
		 * 
		 * @param hash
		 */
		public void addHashCode(long hash) {
			int[] indexs = makeCodeIndex(hash);

			for (int i = 0; i < indexs.length; i++) {
				int idx = indexs[i];
				if (lists[idx] == null) {
					lists[idx] = new ArrayList<Long>();
				}
				lists[idx].add(hash);
			}

		}

		private int[] makeCodeIndex(long hashCode) {
			return new int[] { (int) (hashCode & (BITS[8] - 1)), (int) ((hashCode >>> 8) & (BITS[8] - 1) + 256), (int) ((hashCode >>> 16) & (BITS[8] - 1) + 512),
					(int) ((hashCode >>> 24) & (BITS[8] - 1) + 768), (int) ((hashCode >>> 32) & (BITS[8] - 1) + 1024), (int) ((hashCode >>> 40) & (BITS[8] - 1) + 1280),
					(int) ((hashCode >>> 48) & (BITS[8] - 1) + 1536), (int) ((hashCode >>> 56) & (BITS[8] - 1) + 1792) };
		}

		/**
		 * 增加正文到索引中
		 * 
		 * @param content
		 */
		public void add(String content) {
			addHashCode(fingerprint(content));
		}

		/**
		 * 返回和当前查询最近的汉明距离数字
		 * 
		 * @param hashCode
		 * @return
		 */
		public int nearest(long hashCode) {
			int[] indexs = makeCodeIndex(hashCode);

			Set<Long> sets = new HashSet<Long>();
			for (int i = 0; i < indexs.length; i++) {
				List<Long> list = lists[indexs[i]];
				if (list != null) {
					sets.addAll(list);
				}
			}

			int hmDistance = 64;
			for (Long hc : sets) {
				hmDistance = Math.min(hmDistance(hashCode, hc), hmDistance);
				if (hmDistance == 0) {
					return hmDistance;
				}
			}
			
			return hmDistance;
		}

		/**
		 * 返回和当前查询最近的汉明距离数字
		 * 
		 * @param hashCode
		 * @return
		 */
		public int nearest(String content) {
			return nearest(fingerprint(content));
		}

		/**
		 * 查询最近的距离并且添加到索引中
		 * 
		 * @param hashCode
		 * @return
		 */
		public int nearestAndAdd(long hashCode) {
			int hmDistance = nearest(hashCode);
			if (hmDistance > 0) {
				addHashCode(hashCode);
			}
			return hmDistance;
		}

		/**
		 * 查询最近的距离并且添加到索引中
		 * 
		 * @param hashCode
		 * @return
		 */
		public int nearestAndAdd(String content) {
			return nearestAndAdd(fingerprint(content));
		}

		/**
		 * 得到索引中所有的hashcode
		 * 
		 * @return
		 */
		public Set<Long> allHashCode() {
			Set<Long> hs = new HashSet<Long>();

			for (List<Long> list : lists) {
				if (list != null) {
					for (Long i : list) {
						hs.add(i);
					}
				}
			}

			return hs;
		}
	}

}
