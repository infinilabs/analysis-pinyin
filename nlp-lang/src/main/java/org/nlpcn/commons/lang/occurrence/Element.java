package org.nlpcn.commons.lang.occurrence;

/**
 * Created by ansj on 4/1/14.
 */
public class Element {
	private static final String DEFAULT_NATURE = "";

	private String name;
	private String nature = DEFAULT_NATURE;

	public Element(String name, String nature) {
		if (nature != null) {
			this.nature = nature;
		}
		this.name = name;
	}


	public Element(String name) {
		this.name = name;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Element) {
			Element e1 = (Element) obj;
			return this.name.equals(e1.name) && this.nature.equals(e1.nature);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
