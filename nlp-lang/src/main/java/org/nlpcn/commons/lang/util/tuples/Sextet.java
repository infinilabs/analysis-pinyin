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

/**
 * <p>
 * A tuple of six elements.
 * </p> 
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public final class Sextet<A,B,C,D,E,F>
        extends Tuple
        implements IValue0<A>,
                   IValue1<B>,
                   IValue2<C>,
                   IValue3<D>,
                   IValue4<E>,
                   IValue5<F> {

    private static final long serialVersionUID = -367678052827219823L;

    private static final int SIZE = 6;

    private final A val0;
    private final B val1;
    private final C val2;
    private final D val3;
    private final E val4;
    private final F val5;
    
    
    
    public static <A,B,C,D,E,F> Sextet<A,B,C,D,E,F> with(final A value0, final B value1, final C value2, final D value3, final E value4, final F value5) {
        return new Sextet<A,B,C,D,E,F>(value0,value1,value2,value3,value4,value5);
    }

    
    /**
     * <p>
     * Create tuple from array. Array has to have exactly six elements.
     * </p>
     * 
     * @param <X> the array component type 
     * @param array the array to be converted to a tuple
     * @return the tuple
     */
    public static <X> Sextet<X,X,X,X,X,X> fromArray(final X[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (array.length != 6) {
            throw new IllegalArgumentException("Array must have exactly 6 elements in order to create a Sextet. Size is " + array.length);
        }
        return new Sextet<X,X,X,X,X,X>(
                array[0],array[1],array[2],array[3],array[4],
                array[5]);
    }

    
    /**
     * <p>
     * Create tuple from collection. Collection has to have exactly six elements.
     * </p>
     * 
     * @param <X> the collection component type 
     * @param collection the collection to be converted to a tuple
     * @return the tuple
     */
    public static <X> Sextet<X,X,X,X,X,X> fromCollection(final Collection<X> collection) {
        return fromIterable(collection);
    }

    
    
    /**
     * <p>
     * Create tuple from iterable. Iterable has to have exactly six elements.
     * </p>
     * 
     * @param <X> the iterable component type 
     * @param iterable the iterable to be converted to a tuple
     * @return the tuple
     */
    public static <X> Sextet<X,X,X,X,X,X> fromIterable(final Iterable<X> iterable) {
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
    public static <X> Sextet<X,X,X,X,X,X> fromIterable(final Iterable<X> iterable, int index) {
        return fromIterable(iterable, index, false);
    }

    
    

    private static <X> Sextet<X,X,X,X,X,X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {
        
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
        
        if (tooFewElements && exactSize) {
            throw new IllegalArgumentException("Not enough elements for creating a Sextet (6 needed)");
        }
        
        if (iter.hasNext() && exactSize) {
            throw new IllegalArgumentException("Iterable must have exactly 6 available elements in order to create a Sextet.");
        }
        
        return new Sextet<X,X,X,X,X,X>(
                element0, element1, element2, element3, element4,
                element5);
        
    }
    
    
    
    
    public Sextet(
            final A value0,
            final B value1,
            final C value2,
            final D value3,
            final E value4,
            final F value5) {
        super(value0, value1, value2, value3, value4, value5);
        this.val0 = value0;
        this.val1 = value1;
        this.val2 = value2;
        this.val3 = value3;
        this.val4 = value4;
        this.val5 = value5;
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


    @Override
    public int getSize() {
        return SIZE;
    }
    
    
    
    

    
    
    
    public <X0> Septet<X0,A,B,C,D,E,F> addAt0(final X0 value0) {
        return new Septet<X0,A,B,C,D,E,F>(
                value0, this.val0, this.val1, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0> Septet<A,X0,B,C,D,E,F> addAt1(final X0 value0) {
        return new Septet<A,X0,B,C,D,E,F>(
                this.val0, value0, this.val1, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0> Septet<A,B,X0,C,D,E,F> addAt2(final X0 value0) {
        return new Septet<A,B,X0,C,D,E,F>(
                this.val0, this.val1, value0, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0> Septet<A,B,C,X0,D,E,F> addAt3(final X0 value0) {
        return new Septet<A,B,C,X0,D,E,F>(
                this.val0, this.val1, this.val2, value0, this.val3, this.val4, this.val5);
    }
    
    public <X0> Septet<A,B,C,D,X0,E,F> addAt4(final X0 value0) {
        return new Septet<A,B,C,D,X0,E,F>(
                this.val0, this.val1, this.val2, this.val3, value0, this.val4, this.val5);
    }
    
    public <X0> Septet<A,B,C,D,E,X0,F> addAt5(final X0 value0) {
        return new Septet<A,B,C,D,E,X0,F>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value0, this.val5);
    }
    
    public <X0> Septet<A,B,C,D,E,F,X0> addAt6(final X0 value0) {
        return new Septet<A,B,C,D,E,F,X0>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, value0);
    }

    
    
    
    
    public <X0,X1> Octet<X0,X1,A,B,C,D,E,F> addAt0(final X0 value0, final X1 value1) {
        return new Octet<X0,X1,A,B,C,D,E,F>(
                value0, value1, this.val0, this.val1, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1> Octet<A,X0,X1,B,C,D,E,F> addAt1(final X0 value0, final X1 value1) {
        return new Octet<A,X0,X1,B,C,D,E,F>(
                this.val0, value0, value1, this.val1, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1> Octet<A,B,X0,X1,C,D,E,F> addAt2(final X0 value0, final X1 value1) {
        return new Octet<A,B,X0,X1,C,D,E,F>(
                this.val0, this.val1, value0, value1, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1> Octet<A,B,C,X0,X1,D,E,F> addAt3(final X0 value0, final X1 value1) {
        return new Octet<A,B,C,X0,X1,D,E,F>(
                this.val0, this.val1, this.val2, value0, value1, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1> Octet<A,B,C,D,X0,X1,E,F> addAt4(final X0 value0, final X1 value1) {
        return new Octet<A,B,C,D,X0,X1,E,F>(
                this.val0, this.val1, this.val2, this.val3, value0, value1, this.val4, this.val5);
    }
    
    public <X0,X1> Octet<A,B,C,D,E,X0,X1,F> addAt5(final X0 value0, final X1 value1) {
        return new Octet<A,B,C,D,E,X0,X1,F>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value0, value1, this.val5);
    }
    
    public <X0,X1> Octet<A,B,C,D,E,F,X0,X1> addAt6(final X0 value0, final X1 value1) {
        return new Octet<A,B,C,D,E,F,X0,X1>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, value0, value1);
    }
    


    
    
    
    
    public <X0,X1,X2> Ennead<X0,X1,X2,A,B,C,D,E,F> addAt0(final X0 value0, final X1 value1, final X2 value2) {
        return new Ennead<X0,X1,X2,A,B,C,D,E,F>(
                value0, value1, value2, this.val0, this.val1, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1,X2> Ennead<A,X0,X1,X2,B,C,D,E,F> addAt1(final X0 value0, final X1 value1, final X2 value2) {
        return new Ennead<A,X0,X1,X2,B,C,D,E,F>(
                this.val0, value0, value1, value2, this.val1, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1,X2> Ennead<A,B,X0,X1,X2,C,D,E,F> addAt2(final X0 value0, final X1 value1, final X2 value2) {
        return new Ennead<A,B,X0,X1,X2,C,D,E,F>(
                this.val0, this.val1, value0, value1, value2, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1,X2> Ennead<A,B,C,X0,X1,X2,D,E,F> addAt3(final X0 value0, final X1 value1, final X2 value2) {
        return new Ennead<A,B,C,X0,X1,X2,D,E,F>(
                this.val0, this.val1, this.val2, value0, value1, value2, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1,X2> Ennead<A,B,C,D,X0,X1,X2,E,F> addAt4(final X0 value0, final X1 value1, final X2 value2) {
        return new Ennead<A,B,C,D,X0,X1,X2,E,F>(
                this.val0, this.val1, this.val2, this.val3, value0, value1, value2, this.val4, this.val5);
    }
    
    public <X0,X1,X2> Ennead<A,B,C,D,E,X0,X1,X2,F> addAt5(final X0 value0, final X1 value1, final X2 value2) {
        return new Ennead<A,B,C,D,E,X0,X1,X2,F>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value0, value1, value2, this.val5);
    }
    
    public <X0,X1,X2> Ennead<A,B,C,D,E,F,X0,X1,X2> addAt6(final X0 value0, final X1 value1, final X2 value2) {
        return new Ennead<A,B,C,D,E,F,X0,X1,X2>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, value0, value1, value2);
    }
    


    
    
    
    
    public <X0,X1,X2,X3> Decade<X0,X1,X2,X3,A,B,C,D,E,F> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Decade<X0,X1,X2,X3,A,B,C,D,E,F>(
                value0, value1, value2, value3, this.val0, this.val1, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1,X2,X3> Decade<A,X0,X1,X2,X3,B,C,D,E,F> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Decade<A,X0,X1,X2,X3,B,C,D,E,F>(
                this.val0, value0, value1, value2, value3, this.val1, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1,X2,X3> Decade<A,B,X0,X1,X2,X3,C,D,E,F> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Decade<A,B,X0,X1,X2,X3,C,D,E,F>(
                this.val0, this.val1, value0, value1, value2, value3, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1,X2,X3> Decade<A,B,C,X0,X1,X2,X3,D,E,F> addAt3(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Decade<A,B,C,X0,X1,X2,X3,D,E,F>(
                this.val0, this.val1, this.val2, value0, value1, value2, value3, this.val3, this.val4, this.val5);
    }
    
    public <X0,X1,X2,X3> Decade<A,B,C,D,X0,X1,X2,X3,E,F> addAt4(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Decade<A,B,C,D,X0,X1,X2,X3,E,F>(
                this.val0, this.val1, this.val2, this.val3, value0, value1, value2, value3, this.val4, this.val5);
    }
    
    public <X0,X1,X2,X3> Decade<A,B,C,D,E,X0,X1,X2,X3,F> addAt5(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Decade<A,B,C,D,E,X0,X1,X2,X3,F>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value0, value1, value2, value3, this.val5);
    }
    
    public <X0,X1,X2,X3> Decade<A,B,C,D,E,F,X0,X1,X2,X3> addAt6(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Decade<A,B,C,D,E,F,X0,X1,X2,X3>(
                this.val0, this.val1, this.val2, this.val3, this.val4, this.val5, value0, value1, value2, value3);
    }

    
    
    
    
    public <X0> Septet<X0,A,B,C,D,E,F> addAt0(final Unit<X0> tuple) {
        return addAt0(tuple.getValue0());
    }
    
    public <X0> Septet<A,X0,B,C,D,E,F> addAt1(final Unit<X0> tuple) {
        return addAt1(tuple.getValue0());
    }
    
    public <X0> Septet<A,B,X0,C,D,E,F> addAt2(final Unit<X0> tuple) {
        return addAt2(tuple.getValue0());
    }
    
    public <X0> Septet<A,B,C,X0,D,E,F> addAt3(final Unit<X0> tuple) {
        return addAt3(tuple.getValue0());
    }
    
    public <X0> Septet<A,B,C,D,X0,E,F> addAt4(final Unit<X0> tuple) {
        return addAt4(tuple.getValue0());
    }
    
    public <X0> Septet<A,B,C,D,E,X0,F> addAt5(final Unit<X0> tuple) {
        return addAt5(tuple.getValue0());
    }
    
    public <X0> Septet<A,B,C,D,E,F,X0> addAt6(final Unit<X0> tuple) {
        return addAt6(tuple.getValue0());
    }
    


    
    
    
    
    public <X0,X1> Octet<X0,X1,A,B,C,D,E,F> addAt0(final Pair<X0,X1> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Octet<A,X0,X1,B,C,D,E,F> addAt1(final Pair<X0,X1> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Octet<A,B,X0,X1,C,D,E,F> addAt2(final Pair<X0,X1> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Octet<A,B,C,X0,X1,D,E,F> addAt3(final Pair<X0,X1> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Octet<A,B,C,D,X0,X1,E,F> addAt4(final Pair<X0,X1> tuple) {
        return addAt4(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Octet<A,B,C,D,E,X0,X1,F> addAt5(final Pair<X0,X1> tuple) {
        return addAt5(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Octet<A,B,C,D,E,F,X0,X1> addAt6(final Pair<X0,X1> tuple) {
        return addAt6(tuple.getValue0(),tuple.getValue1());
    }

    
    

    
    
    
    
    public <X0,X1,X2> Ennead<X0,X1,X2,A,B,C,D,E,F> addAt0(final Triplet<X0,X1,X2> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Ennead<A,X0,X1,X2,B,C,D,E,F> addAt1(final Triplet<X0,X1,X2> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Ennead<A,B,X0,X1,X2,C,D,E,F> addAt2(final Triplet<X0,X1,X2> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Ennead<A,B,C,X0,X1,X2,D,E,F> addAt3(final Triplet<X0,X1,X2> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Ennead<A,B,C,D,X0,X1,X2,E,F> addAt4(final Triplet<X0,X1,X2> tuple) {
        return addAt4(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Ennead<A,B,C,D,E,X0,X1,X2,F> addAt5(final Triplet<X0,X1,X2> tuple) {
        return addAt5(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Ennead<A,B,C,D,E,F,X0,X1,X2> addAt6(final Triplet<X0,X1,X2> tuple) {
        return addAt6(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    
    


    
    
    
    
    public <X0,X1,X2,X3> Decade<X0,X1,X2,X3,A,B,C,D,E,F> addAt0(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Decade<A,X0,X1,X2,X3,B,C,D,E,F> addAt1(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Decade<A,B,X0,X1,X2,X3,C,D,E,F> addAt2(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Decade<A,B,C,X0,X1,X2,X3,D,E,F> addAt3(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Decade<A,B,C,D,X0,X1,X2,X3,E,F> addAt4(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt4(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Decade<A,B,C,D,E,X0,X1,X2,X3,F> addAt5(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt5(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Decade<A,B,C,D,E,F,X0,X1,X2,X3> addAt6(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt6(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }

    
    
    
    
    
    
    
    
    public <X0> Septet<A,B,C,D,E,F,X0> add(final X0 value0) {
        return addAt6(value0);
    }
    
    
    public <X0> Septet<A,B,C,D,E,F,X0> add(final Unit<X0> tuple) {
        return addAt6(tuple);
    }
    
    
    
    
    public <X0,X1> Octet<A,B,C,D,E,F,X0,X1> add(final X0 value0, final X1 value1) {
        return addAt6(value0, value1);
    }
    
    
    public <X0,X1> Octet<A,B,C,D,E,F,X0,X1> add(final Pair<X0,X1> tuple) {
        return addAt6(tuple);
    }
    
    
    
    
    public <X0,X1,X2> Ennead<A,B,C,D,E,F,X0,X1,X2> add(final X0 value0, final X1 value1, final X2 value2) {
        return addAt6(value0, value1, value2);
    }
    
    
    public <X0,X1,X2> Ennead<A,B,C,D,E,F,X0,X1,X2> add(final Triplet<X0,X1,X2> tuple) {
        return addAt6(tuple);
    }
    
    
    
    
    public <X0,X1,X2,X3> Decade<A,B,C,D,E,F,X0,X1,X2,X3> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return addAt6(value0, value1, value2, value3);
    }
    
    
    public <X0,X1,X2,X3> Decade<A,B,C,D,E,F,X0,X1,X2,X3> add(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt6(tuple);
    }
    
    

    
    
    
    
    
    public <X> Sextet<X,B,C,D,E,F> setAt0(final X value) {
        return new Sextet<X,B,C,D,E,F>(
                value, this.val1, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X> Sextet<A,X,C,D,E,F> setAt1(final X value) {
        return new Sextet<A,X,C,D,E,F>(
                this.val0, value, this.val2, this.val3, this.val4, this.val5);
    }
    
    public <X> Sextet<A,B,X,D,E,F> setAt2(final X value) {
        return new Sextet<A,B,X,D,E,F>(
                this.val0, this.val1, value, this.val3, this.val4, this.val5);
    }
    
    public <X> Sextet<A,B,C,X,E,F> setAt3(final X value) {
        return new Sextet<A,B,C,X,E,F>(
                this.val0, this.val1, this.val2, value, this.val4, this.val5);
    }
    
    public <X> Sextet<A,B,C,D,X,F> setAt4(final X value) {
        return new Sextet<A,B,C,D,X,F>(
                this.val0, this.val1, this.val2, this.val3, value, this.val5);
    }
    
    public <X> Sextet<A,B,C,D,E,X> setAt5(final X value) {
        return new Sextet<A,B,C,D,E,X>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value);
    }
    
    
    
    
    
    
    
    public Quintet<B,C,D,E,F> removeFrom0() {
        return new Quintet<B,C,D,E,F>(
                this.val1, this.val2, this.val3, this.val4, this.val5);
    }
    
    public Quintet<A,C,D,E,F> removeFrom1() {
        return new Quintet<A,C,D,E,F>(
                this.val0, this.val2, this.val3, this.val4, this.val5);
    }
    
    public Quintet<A,B,D,E,F> removeFrom2() {
        return new Quintet<A,B,D,E,F>(
                this.val0, this.val1, this.val3, this.val4, this.val5);
    }
    
    public Quintet<A,B,C,E,F> removeFrom3() {
        return new Quintet<A,B,C,E,F>(
                this.val0, this.val1, this.val2, this.val4, this.val5);
    }
    
    public Quintet<A,B,C,D,F> removeFrom4() {
        return new Quintet<A,B,C,D,F>(
                this.val0, this.val1, this.val2, this.val3, this.val5);
    }
    
    public Quintet<A,B,C,D,E> removeFrom5() {
        return new Quintet<A,B,C,D,E>(
                this.val0, this.val1, this.val2, this.val3, this.val4);
    }
    
    
    
    
}
