package org.nlpcn.commons.lang.bloomFilter.iface;

public interface BitMap {
	
	public final int MACHINE32 = 32 ;

	public final int MACHINE64 = 32 ;
	
	public void add(long i) ;
	public boolean contains(long i) ;
	public void remove(long i) ;
}
