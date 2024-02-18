/*
 * =============================================================================
 * 
 *   Copyright (c) 2010, The JAVATUPLES team (http://www.javatuples.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.nlpcn.commons.lang.util.tuples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * Abstract base class for all tuple classes.
 * </p> 
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public abstract class Tuple implements Iterable<Object>, Serializable, Comparable<Tuple> {

    private static final long serialVersionUID = 5431085632328343101L;
    
    private final Object[] valueArray;
    private final List<Object> valueList;
    
    

    /**
     * 
     * @deprecated Will be removed in 1.4. The "size" parameter is of no use at
     *             this level, so use the simpler Tuple(values) constructor instead.
     */
    @Deprecated
    protected Tuple(@SuppressWarnings("unused") final int size, final Object... values) {
        super();
        this.valueArray = values;
        this.valueList = Arrays.asList(values);
    }


    
    protected Tuple(final Object... values) {
        super();
        this.valueArray = values;
        this.valueList = Arrays.asList(values);
    }
    

    /**
     * <p>
     * Return the size of the tuple.
     * </p>
     * 
     * @return the size of the tuple.
     */
    public abstract int getSize();

    
    /**
     * <p>
     * Get the value at a specific position in the tuple. This method
     * has to return object, so using it you will lose the type-safety you 
     * get with the <tt>getValueX()</tt> methods.
     * </p>
     * 
     * @param pos the position of the value to be retrieved.
     * @return the value
     */
    public final Object getValue(final int pos) {
        if (pos >= getSize()) {
            throw new IllegalArgumentException(
                    "Cannot retrieve position " + pos + " in " + this.getClass().getSimpleName() + 
                    ". Positions for this class start with 0 and end with " + (getSize() - 1));
        }
        return this.valueArray[pos];
    }
    

    
    public final Iterator<Object> iterator() {
        return this.valueList.iterator();
    }
    
    
    @Override
    public final String toString() {
        return this.valueList.toString();
    }
    
    
    public final boolean contains(final Object value) {
        for (final Object val : this.valueList) {
            if (val == null) {
                if (value == null) {
                    return true;
                }
            } else {
                if (val.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    public final boolean containsAll(final Collection<?> collection) {
        for (final Object value : collection) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }
    
    
    public final boolean containsAll(final Object... values) {
        if (values == null) {
            throw new IllegalArgumentException("Values array cannot be null");
        }
        for (final Object value : values) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    
    
    public final int indexOf(final Object value) {
        int i = 0;
        for (final Object val : this.valueList) {
            if (val == null) {
                if (value == null) {
                    return i;
                }
            } else {
                if (val.equals(value)) {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }
    
    
    public final int lastIndexOf(final Object value) {
        for (int i = getSize() - 1; i >= 0; i--) {
            final Object val = this.valueList.get(i);
            if (val == null) {
                if (value == null) {
                    return i;
                }
            } else {
                if (val.equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }
    

    
    
    
    public final List<Object> toList() {
        return Collections.unmodifiableList(new ArrayList<Object>(this.valueList));
    }
    
    
    
    public final Object[] toArray() {
        return this.valueArray.clone();
    }
    
    

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((this.valueList == null) ? 0 : this.valueList.hashCode());
        return result;
    }



    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tuple other = (Tuple) obj;
        return this.valueList.equals(other.valueList);
    }



    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int compareTo(final Tuple o) {
        
        final int tLen = this.valueArray.length;
        final Object[] oValues = o.valueArray;
        final int oLen = oValues.length;
        
        for (int i = 0; i < tLen && i < oLen; i++) {
            
            final Comparable tElement = (Comparable)this.valueArray[i];
            final Comparable oElement = (Comparable)oValues[i];
            
            final int comparison = tElement.compareTo(oElement);
            if (comparison != 0) {
                return comparison;
            }
            
        }
        
        return (Integer.valueOf(tLen)).compareTo(Integer.valueOf(oLen));
        
    }
    
    
    
}
