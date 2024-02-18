package org.nlpcn.commons.lang.occurrence;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ansj on 4/10/14.
 */
public class Count implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id = 0;
	double score = 1; //这个词
	int relationCount; //这个词和其他词共同出现多少次
	String nature;

	Set<Integer> relationSet = new HashSet<Integer>();

	public Count(String nature, int id) {
		this.nature = nature;
		this.id = id;
	}


	public void upScore() {
		this.score++;
	}

	public void upRelation(int rId) {
		this.relationCount++;
		this.relationSet.add(rId);
	}


	@Override
	public String toString() {
		return this.id + "\t" + this.score + "\t" + this.relationCount;
	}
}