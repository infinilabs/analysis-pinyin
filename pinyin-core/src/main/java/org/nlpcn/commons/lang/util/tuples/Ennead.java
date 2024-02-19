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

import java.util.Collection;
import java.util.Iterator;

import org.nlpcn.commons.lang.util.tuples.valueintf.IValue0;
import org.nlpcn.commons.lang.util.tuples.valueintf.IValue1;
import org.nlpcn.commons.lang.util.tuples.valueintf.IValue2;
import org.nlpcn.commons.lang.util.tuples.valueintf.IValue3;
import org.nlpcn.commons.lang.util.tuples.valueintf.IValue4;
import org.nlpcn.commons.lang.util.tuples.valueintf.IValue5;
import org.nlpcn.commons.lang.util.tuples.valueintf.IValue6;
import org.nlpcn.commons.lang.util.tuples.valueintf.IValue7;
import org.nlpcn.commons.lang.util.tuples.valueintf.IValue8;

/**
 * <p>
 * A tuple of nine elements.
 * </p> 
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public final class Ennead<A,B,C,D,E,F,G,H,I>
        extends Tuple
        implements IValue0<A>,
                   IValue1<B>,
                   IValue2<C>,
                   IValue3<D>,
                   IValue4<E>,
                   IValue5<F>,
                   IValue6<G>,
                   IValue7<H>,
                   IValue8<I> {

    private static final long serialVersionUID = -4782141390960730966L;

    private static final int SIZE = 9;

    private final A val0;
    private final B val1;
    private final C val2;
    private final D val3;
    private final E val4;
    private final F val5;
    private final G val6;
    private final H val7;
    private final I val8;
    
    
    
    public static <A,B,C,D,E,F,G,H,I> Ennead<A,B,C,D,E,F,G,H,I> with(final A value0, final B value1, final C value2, final D value3, final E value4, final F value5, final G value6, final H value7, final I value8) {
        return new Ennead<A,B,C,D,E,F,G,H,I>(value0,value1,value2,value3,value4,value5,value6,value7,value8);
    }

    
    /**
     * <p>
     * Create tuple from array. Array has to have exactly nine elements.
     * </p>
     * 
     * @param <X> the array component type 
     * @param array the array to be converted to a tuple
     * @return the tuple
     */
    public static <X> Ennead<X,X,X,X,X,X,X,X,X> fromArray(final X[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (array.length != 9) {
            throw new IllegalArgumentException("Array must have exactly 9 elements in order to create an Ennead. Size is " + array.length);
        }
        return new Ennead<X,X,X,X,X,X,X,X,X>(
                array[0],array[1],array[2],array[3],array[4],
                array[5],array[6],array[7],array[8]);
    }

    
    /**
     * <p>
     * Create tuple from collection. Collection has to have exactly nine elements.
     * </p>
     * 
     * @param <X> the collection component type 
     * @param collection the collection to be converted to a tuple
     * @return the tuple
     */
    public static <X> Ennead<X,X,X,X,X,X,X,X,X> fromCollection(final Collection<X> collection) {
        return fromIterable(collection);
    }

    

    /**
     * <p>
     * Create tuple from iterable. Iterable has to have exactly nine elements.
     * </p>
     * 
     * @param <X> the iterable component type 
     * @param iterable the iterable to be converted to a tuple
     * @return the tuple
     */
    public static <X> Ennead<X,X,X,X,X,X,X,X,X> fromIterable(final Iterable<X> iterable) {
        return fromIterable(iterable, 0, true);
    }

    
    
    /**
     * <p>
     * Create tuple from iterable, starting from the specified index. Iterable
     * can have more (or less) elements than the tuple to be created.
     * </p>
     * 
     * @param <X> the iterable component type 
     * @param iterable the iterable to be converted to a tuple
     * @return the tuple
     */
    public static <X> Ennead<X,X,X,X,X,X,X,X,X> fromIterable(final Iterable<X> iterable, int index) {
        return fromIterable(iterable, index, false);
    }
    

    

    private static <X> Ennead<X,X,X,X,X,X,X,X,X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {
        
        if (iterable == null) {
            throw new IllegalArgumentException("Iterable cannot be null");
        }

        boolean tooFewElements = false; 
        
        X element0 = null;
        X element1 = null;
        X element2 = null;
        X element3 = null;
        X element4 = null;
        X element5 = null;
        X element6 = null;
        X element7 = null;
        X element8 = null;
        
        final Iterator<X> iter = iterable.iterator();
        
        int i = 0;
        while (i < index) {
            if (iter.hasNext()) {
                iter.next();
            } else {
                tooFewElements = true;
            }
            i++;
        }
        
        if (iter.hasNext()) {
            element0 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (iter.hasNext()) {
            element1 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (iter.hasNext()) {
            element2 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (iter.hasNext()) {
            element3 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (iter.hasNext()) {
            element4 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (iter.hasNext()) {
            element5 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (iter.hasNext()) {
            element6 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (iter.hasNext()) {
            element7 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (iter.hasNext()) {
            element8 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (tooFewElements && exactSize) {
            throw new IllegalArgumentException("Not enough elements for creating an Ennead (9 needed)");
        }
        
        if (iter.hasNext() && exactSize) {
            throw new IllegalArgumentException("Iterable must have exactly 9 available elements in order to create an Ennead.");
        }
        
        return new Ennead<X,X,X,X,X,X,X,X,X>(
                element0, element1, element2, element3, element4,
                element5, element6, element7, element8);
        
    }
    
    
    
    public Ennead(
            final A value0,
            final B value1,
            final C value2,
            final D value3,
            final E value4,
            final F value5,
            final G value6,
            final H value7,
            final I value8) {
        super(value0, value1, value2, value3, value4, value5, value6, value7, value8);
        this.val0 = value0;
        this.val1 = value1;
        this.val2 = value2;
        this.val3 = value3;
        this.val4 = value4;
        this.val5 = value5;
        this.val6 = value6;
        this.val7 = value7;
        this.val8 = value8;
    }


    public A getValue0() {
        return this.val0;
    }


    public B getValue1() {
        return this.val1;
    }


    public C getValue2() {
        return this.val2;
    }


    public D getValue3() {
        return this.val3;
    }


    public E getValue4() {
        return this.val4;
    }


    public F getValue5() {
        return this.val5;
    }


    public G getValue6() {
        return this.val6;
    }

    
    public H getValue7() {
        return this.val7;
    }


    public I getValue8() {
        return this.val8;
    }


    @Override
    public int getSize() {
        return SIZE;
    }

    
    
    
    public <X0> Decade<X0,A,B,C,D,E,F,G,H,I> addAt0(final X0 value0) {
        return new Decade<X0,A,B,C,D,E,F,G,H,I>(
                value0, this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, 
                this.val6, this.val7, this.val8);
    }
    
    public <X0> Decade<A,X0,B,C,D,E,F,G,H,I> addAt1(final X0 value0) {
        return new Decade<A,X0,B,C,D,E,F,G,H,I>(
                this.val0, value0, this.val1, this.val2, this.val3, this.val4, this.val5, 
                this.val6, this.val7, this.val8);
    }
    
    public <X0> Decade<A,B,X0,C,D,E,F,G,H,I> addAt2(final X0 value0) {
        return new Decade<A,B,X0,C,D,E,F,G,H,I>(
                this.val0, this.val1, value0, this.val2, this.val3, this.val4, this.val5, 
                this.val6, this.val7, this.val8);
    }
    
    public <X0> Decade<A,B,C,X0,D,E,F,G,H,I> addAt3(final X0 value0) {
        return new Decade<A,B,C,X0,D,E,F,G,H,I>(
                this.val0, this.val1, this.val2, value0, this.val3, this.val4, this.val5, 
                this.val6, this.val7, this.val8);
    }
    
    public <X0> Decade<A,B,C,D,X0,E,F,G,H,I> addAt4(final X0 value0) {
        return new Decade<A,B,C,D,X0,E,F,G,H,I>(
                this.val0, this.val1, this.val2, this.val3, value0, this.val4, this.val5, 
                this.val6, this.val7, this.val8);
    }
    
    public <X0> Decade<A,B,C,D,E,X0,F,G,H,I> addAt5(final X0 value0) {
        return new Decade<A,B,C,D,E,X0,F,G,H,I>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value0, this.val5, 
                this.val6, this.val7, this.val8);
    }
    
    public <X0> Decade<A,B,C,D,E,F,X0,G,H,I> addAt6(final X0 value0) {
        return new Decade<A,B,C,D,E,F,X0,G,H,I>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, value0, 
                this.val6, this.val7, this.val8);
    }
    
    public <X0> Decade<A,B,C,D,E,F,G,X0,H,I> addAt7(final X0 value0) {
        return new Decade<A,B,C,D,E,F,G,X0,H,I>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, this.val6, 
                value0, this.val7, this.val8);
    }
    
    public <X0> Decade<A,B,C,D,E,F,G,H,X0,I> addAt8(final X0 value0) {
        return new Decade<A,B,C,D,E,F,G,H,X0,I>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, this.val6, 
                this.val7, value0, this.val8);
    }
    
    public <X0> Decade<A,B,C,D,E,F,G,H,I,X0> addAt9(final X0 value0) {
        return new Decade<A,B,C,D,E,F,G,H,I,X0>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, this.val6, 
                this.val7, this.val8, value0);
    }

    
    
    public <X0> Decade<X0,A,B,C,D,E,F,G,H,I> addAt0(final Unit<X0> tuple) {
        return addAt0(tuple.getValue0());
    }
    
    public <X0> Decade<A,X0,B,C,D,E,F,G,H,I> addAt1(final Unit<X0> tuple) {
        return addAt1(tuple.getValue0());
    }
    
    public <X0> Decade<A,B,X0,C,D,E,F,G,H,I> addAt2(final Unit<X0> tuple) {
        return addAt2(tuple.getValue0());
    }
    
    public <X0> Decade<A,B,C,X0,D,E,F,G,H,I> addAt3(final Unit<X0> tuple) {
        return addAt3(tuple.getValue0());
    }
    
    public <X0> Decade<A,B,C,D,X0,E,F,G,H,I> addAt4(final Unit<X0> tuple) {
        return addAt4(tuple.getValue0());
    }
    
    public <X0> Decade<A,B,C,D,E,X0,F,G,H,I> addAt5(final Unit<X0> tuple) {
        return addAt5(tuple.getValue0());
    }
    
    public <X0> Decade<A,B,C,D,E,F,X0,G,H,I> addAt6(final Unit<X0> tuple) {
        return addAt6(tuple.getValue0());
    }
    
    public <X0> Decade<A,B,C,D,E,F,G,X0,H,I> addAt7(final Unit<X0> tuple) {
        return addAt7(tuple.getValue0());
    }
    
    public <X0> Decade<A,B,C,D,E,F,G,H,X0,I> addAt8(final Unit<X0> tuple) {
        return addAt8(tuple.getValue0());
    }
    
    public <X0> Decade<A,B,C,D,E,F,G,H,I,X0> addAt9(final Unit<X0> tuple) {
        return addAt9(tuple.getValue0());
    }
    

    
    
    public <X0> Decade<A,B,C,D,E,F,G,H,I,X0> add(final X0 value0) {
        return addAt9(value0);
    }
    
    
    public <X0> Decade<A,B,C,D,E,F,G,H,I,X0> add(final Unit<X0> tuple) {
        return addAt9(tuple);
    }
    
    
    
    
    
    public <X> Ennead<X,B,C,D,E,F,G,H,I> setAt0(final X value) {
        return new Ennead<X,B,C,D,E,F,G,H,I>(
                value, this.val1, this.val2, this.val3, this.val4, this.val5, this.val6, this.val7, this.val8);
    }
    
    public <X> Ennead<A,X,C,D,E,F,G,H,I> setAt1(final X value) {
        return new Ennead<A,X,C,D,E,F,G,H,I>(
                this.val0, value, this.val2, this.val3, this.val4, this.val5, this.val6, this.val7, this.val8);
    }
    
    public <X> Ennead<A,B,X,D,E,F,G,H,I> setAt2(final X value) {
        return new Ennead<A,B,X,D,E,F,G,H,I>(
                this.val0, this.val1, value, this.val3, this.val4, this.val5, this.val6, this.val7, this.val8);
    }
    
    public <X> Ennead<A,B,C,X,E,F,G,H,I> setAt3(final X value) {
        return new Ennead<A,B,C,X,E,F,G,H,I>(
                this.val0, this.val1, this.val2, value, this.val4, this.val5, this.val6, this.val7, this.val8);
    }
    
    public <X> Ennead<A,B,C,D,X,F,G,H,I> setAt4(final X value) {
        return new Ennead<A,B,C,D,X,F,G,H,I>(
                this.val0, this.val1, this.val2, this.val3, value, this.val5, this.val6, this.val7, this.val8);
    }
    
    public <X> Ennead<A,B,C,D,E,X,G,H,I> setAt5(final X value) {
        return new Ennead<A,B,C,D,E,X,G,H,I>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value, this.val6, this.val7, this.val8);
    }
    
    public <X> Ennead<A,B,C,D,E,F,X,H,I> setAt6(final X value) {
        return new Ennead<A,B,C,D,E,F,X,H,I>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, value, this.val7, this.val8);
    }
    
    public <X> Ennead<A,B,C,D,E,F,G,X,I> setAt7(final X value) {
        return new Ennead<A,B,C,D,E,F,G,X,I>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, this.val6, value, this.val8);
    }
    
    public <X> Ennead<A,B,C,D,E,F,G,H,X> setAt8(final X value) {
        return new Ennead<A,B,C,D,E,F,G,H,X>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, this.val6, this.val7, value);
    }
    
    
    
    
    
    
    
    public Octet<B,C,D,E,F,G,H,I> removeFrom0() {
        return new Octet<B,C,D,E,F,G,H,I>(
                this.val1, this.val2, this.val3, this.val4, this.val5, this.val6, this.val7, this.val8);
    }
    
    public Octet<A,C,D,E,F,G,H,I> removeFrom1() {
        return new Octet<A,C,D,E,F,G,H,I>(
                this.val0, this.val2, this.val3, this.val4, this.val5, this.val6, this.val7, this.val8);
    }
    
    public Octet<A,B,D,E,F,G,H,I> removeFrom2() {
        return new Octet<A,B,D,E,F,G,H,I>(
                this.val0, this.val1, this.val3, this.val4, this.val5, this.val6, this.val7, this.val8);
    }
    
    public Octet<A,B,C,E,F,G,H,I> removeFrom3() {
        return new Octet<A,B,C,E,F,G,H,I>(
                this.val0, this.val1, this.val2, this.val4, this.val5, this.val6, this.val7, this.val8);
    }
    
    public Octet<A,B,C,D,F,G,H,I> removeFrom4() {
        return new Octet<A,B,C,D,F,G,H,I>(
                this.val0, this.val1, this.val2, this.val3, this.val5, this.val6, this.val7, this.val8);
    }
    
    public Octet<A,B,C,D,E,G,H,I> removeFrom5() {
        return new Octet<A,B,C,D,E,G,H,I>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val6, this.val7, this.val8);
    }
    
    public Octet<A,B,C,D,E,F,H,I> removeFrom6() {
        return new Octet<A,B,C,D,E,F,H,I>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, this.val7, this.val8);
    }
    
    public Octet<A,B,C,D,E,F,G,I> removeFrom7() {
        return new Octet<A,B,C,D,E,F,G,I>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, this.val6, this.val8);
    }
    
    public Octet<A,B,C,D,E,F,G,H> removeFrom8() {
        return new Octet<A,B,C,D,E,F,G,H>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, this.val6, this.val7);
    }
    

}
