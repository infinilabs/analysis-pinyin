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

/**
 * <p>
 * A tuple of five elements.
 * </p> 
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public final class Quintet<A,B,C,D,E>
        extends Tuple
        implements IValue0<A>,
                   IValue1<B>,
                   IValue2<C>,
                   IValue3<D>,
                   IValue4<E> {

    private static final long serialVersionUID = -1579008485383872628L;

    private static final int SIZE = 5;

    private final A val0;
    private final B val1;
    private final C val2;
    private final D val3;
    private final E val4;
    
    
    
    public static <A,B,C,D,E> Quintet<A,B,C,D,E> with(final A value0, final B value1, final C value2, final D value3, final E value4) {
        return new Quintet<A,B,C,D,E>(value0,value1,value2,value3,value4);
    }

    
    /**
     * <p>
     * Create tuple from array. Array has to have exactly five elements.
     * </p>
     * 
     * @param <X> the array component type 
     * @param array the array to be converted to a tuple
     * @return the tuple
     */
    public static <X> Quintet<X,X,X,X,X> fromArray(final X[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (array.length != 5) {
            throw new IllegalArgumentException("Array must have exactly 5 elements in order to create a Quintet. Size is " + array.length);
        }
        return new Quintet<X,X,X,X,X>(
                array[0],array[1],array[2],array[3],array[4]);
    }

    
    /**
     * <p>
     * Create tuple from collection. Collection has to have exactly five elements.
     * </p>
     * 
     * @param <X> the collection component type 
     * @param collection the collection to be converted to a tuple
     * @return the tuple
     */
    public static <X> Quintet<X,X,X,X,X> fromCollection(final Collection<X> collection) {
        return fromIterable(collection);
    }

    
    
    /**
     * <p>
     * Create tuple from iterable. Iterable has to have exactly five elements.
     * </p>
     * 
     * @param <X> the iterable component type 
     * @param iterable the iterable to be converted to a tuple
     * @return the tuple
     */
    public static <X> Quintet<X,X,X,X,X> fromIterable(final Iterable<X> iterable) {
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
    public static <X> Quintet<X,X,X,X,X> fromIterable(final Iterable<X> iterable, int index) {
        return fromIterable(iterable, index, false);
    }

    
    

    private static <X> Quintet<X,X,X,X,X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {
        
        if (iterable == null) {
            throw new IllegalArgumentException("Iterable cannot be null");
        }

        boolean tooFewElements = false; 
        
        X element0 = null;
        X element1 = null;
        X element2 = null;
        X element3 = null;
        X element4 = null;
        
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
        
        if (tooFewElements && exactSize) {
            throw new IllegalArgumentException("Not enough elements for creating a Quintet (5 needed)");
        }
        
        if (iter.hasNext() && exactSize) {
            throw new IllegalArgumentException("Iterable must have exactly 5 available elements in order to create a Quintet.");
        }
        
        return new Quintet<X,X,X,X,X>(
                element0, element1, element2, element3, element4);
        
    }
    
    
    
    
    public Quintet(
            final A value0,
            final B value1,
            final C value2,
            final D value3,
            final E value4) {
        super(value0, value1, value2, value3, value4);
        this.val0 = value0;
        this.val1 = value1;
        this.val2 = value2;
        this.val3 = value3;
        this.val4 = value4;
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


    @Override
    public int getSize() {
        return SIZE;
    }
    
    
    
    

    
    
    
    public <X0> Sextet<X0,A,B,C,D,E> addAt0(final X0 value0) {
        return new Sextet<X0,A,B,C,D,E>(
                value0, this.val0, this.val1, this.val2, this.val3, this.val4);
    }
    
    public <X0> Sextet<A,X0,B,C,D,E> addAt1(final X0 value0) {
        return new Sextet<A,X0,B,C,D,E>(
                this.val0, value0, this.val1, this.val2, this.val3, this.val4);
    }
    
    public <X0> Sextet<A,B,X0,C,D,E> addAt2(final X0 value0) {
        return new Sextet<A,B,X0,C,D,E>(
                this.val0, this.val1, value0, this.val2, this.val3, this.val4);
    }
    
    public <X0> Sextet<A,B,C,X0,D,E> addAt3(final X0 value0) {
        return new Sextet<A,B,C,X0,D,E>(
                this.val0, this.val1, this.val2, value0, this.val3, this.val4);
    }
    
    public <X0> Sextet<A,B,C,D,X0,E> addAt4(final X0 value0) {
        return new Sextet<A,B,C,D,X0,E>(
                this.val0, this.val1, this.val2, this.val3, value0, this.val4);
    }
    
    public <X0> Sextet<A,B,C,D,E,X0> addAt5(final X0 value0) {
        return new Sextet<A,B,C,D,E,X0>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value0);
    }

    
    
    
    
    public <X0,X1> Septet<X0,X1,A,B,C,D,E> addAt0(final X0 value0, final X1 value1) {
        return new Septet<X0,X1,A,B,C,D,E>(
                value0, value1, this.val0, this.val1, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1> Septet<A,X0,X1,B,C,D,E> addAt1(final X0 value0, final X1 value1) {
        return new Septet<A,X0,X1,B,C,D,E>(
                this.val0, value0, value1, this.val1, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1> Septet<A,B,X0,X1,C,D,E> addAt2(final X0 value0, final X1 value1) {
        return new Septet<A,B,X0,X1,C,D,E>(
                this.val0, this.val1, value0, value1, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1> Septet<A,B,C,X0,X1,D,E> addAt3(final X0 value0, final X1 value1) {
        return new Septet<A,B,C,X0,X1,D,E>(
                this.val0, this.val1, this.val2, value0, value1, this.val3, this.val4);
    }
    
    public <X0,X1> Septet<A,B,C,D,X0,X1,E> addAt4(final X0 value0, final X1 value1) {
        return new Septet<A,B,C,D,X0,X1,E>(
                this.val0, this.val1, this.val2, this.val3, value0, value1, this.val4);
    }
    
    public <X0,X1> Septet<A,B,C,D,E,X0,X1> addAt5(final X0 value0, final X1 value1) {
        return new Septet<A,B,C,D,E,X0,X1>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value0, value1);
    }
    


    
    
    
    
    public <X0,X1,X2> Octet<X0,X1,X2,A,B,C,D,E> addAt0(final X0 value0, final X1 value1, final X2 value2) {
        return new Octet<X0,X1,X2,A,B,C,D,E>(
                value0, value1, value2, this.val0, this.val1, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1,X2> Octet<A,X0,X1,X2,B,C,D,E> addAt1(final X0 value0, final X1 value1, final X2 value2) {
        return new Octet<A,X0,X1,X2,B,C,D,E>(
                this.val0, value0, value1, value2, this.val1, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1,X2> Octet<A,B,X0,X1,X2,C,D,E> addAt2(final X0 value0, final X1 value1, final X2 value2) {
        return new Octet<A,B,X0,X1,X2,C,D,E>(
                this.val0, this.val1, value0, value1, value2, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1,X2> Octet<A,B,C,X0,X1,X2,D,E> addAt3(final X0 value0, final X1 value1, final X2 value2) {
        return new Octet<A,B,C,X0,X1,X2,D,E>(
                this.val0, this.val1, this.val2, value0, value1, value2, this.val3, this.val4);
    }
    
    public <X0,X1,X2> Octet<A,B,C,D,X0,X1,X2,E> addAt4(final X0 value0, final X1 value1, final X2 value2) {
        return new Octet<A,B,C,D,X0,X1,X2,E>(
                this.val0, this.val1, this.val2, this.val3, value0, value1, value2, this.val4);
    }
    
    public <X0,X1,X2> Octet<A,B,C,D,E,X0,X1,X2> addAt5(final X0 value0, final X1 value1, final X2 value2) {
        return new Octet<A,B,C,D,E,X0,X1,X2>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value0, value1, value2);
    }
    


    
    
    
    
    public <X0,X1,X2,X3> Ennead<X0,X1,X2,X3,A,B,C,D,E> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Ennead<X0,X1,X2,X3,A,B,C,D,E>(
                value0, value1, value2, value3, this.val0, this.val1, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1,X2,X3> Ennead<A,X0,X1,X2,X3,B,C,D,E> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Ennead<A,X0,X1,X2,X3,B,C,D,E>(
                this.val0, value0, value1, value2, value3, this.val1, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1,X2,X3> Ennead<A,B,X0,X1,X2,X3,C,D,E> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Ennead<A,B,X0,X1,X2,X3,C,D,E>(
                this.val0, this.val1, value0, value1, value2, value3, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1,X2,X3> Ennead<A,B,C,X0,X1,X2,X3,D,E> addAt3(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Ennead<A,B,C,X0,X1,X2,X3,D,E>(
                this.val0, this.val1, this.val2, value0, value1, value2, value3, this.val3, this.val4);
    }
    
    public <X0,X1,X2,X3> Ennead<A,B,C,D,X0,X1,X2,X3,E> addAt4(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Ennead<A,B,C,D,X0,X1,X2,X3,E>(
                this.val0, this.val1, this.val2, this.val3, value0, value1, value2, value3, this.val4);
    }
    
    public <X0,X1,X2,X3> Ennead<A,B,C,D,E,X0,X1,X2,X3> addAt5(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Ennead<A,B,C,D,E,X0,X1,X2,X3>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value0, value1, value2, value3);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4> Decade<X0,X1,X2,X3,X4,A,B,C,D,E> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Decade<X0,X1,X2,X3,X4,A,B,C,D,E>(
                value0, value1, value2, value3, value4, this.val0, this.val1, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1,X2,X3,X4> Decade<A,X0,X1,X2,X3,X4,B,C,D,E> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Decade<A,X0,X1,X2,X3,X4,B,C,D,E>(
                this.val0, value0, value1, value2, value3, value4, this.val1, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1,X2,X3,X4> Decade<A,B,X0,X1,X2,X3,X4,C,D,E> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Decade<A,B,X0,X1,X2,X3,X4,C,D,E>(
                this.val0, this.val1, value0, value1, value2, value3, value4, this.val2, this.val3, this.val4);
    }
    
    public <X0,X1,X2,X3,X4> Decade<A,B,C,X0,X1,X2,X3,X4,D,E> addAt3(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Decade<A,B,C,X0,X1,X2,X3,X4,D,E>(
                this.val0, this.val1, this.val2, value0, value1, value2, value3, value4, this.val3, this.val4);
    }
    
    public <X0,X1,X2,X3,X4> Decade<A,B,C,D,X0,X1,X2,X3,X4,E> addAt4(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Decade<A,B,C,D,X0,X1,X2,X3,X4,E>(
                this.val0, this.val1, this.val2, this.val3, value0, value1, value2, value3, value4, this.val4);
    }
    
    public <X0,X1,X2,X3,X4> Decade<A,B,C,D,E,X0,X1,X2,X3,X4> addAt5(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Decade<A,B,C,D,E,X0,X1,X2,X3,X4>(
                this.val0, this.val1, this.val2, this.val3, this.val4, value0, value1, value2, value3, value4);
    }

    
    
    
    
    
    
    public <X0> Sextet<X0,A,B,C,D,E> addAt0(final Unit<X0> tuple) {
        return addAt0(tuple.getValue0());
    }
    
    public <X0> Sextet<A,X0,B,C,D,E> addAt1(final Unit<X0> tuple) {
        return addAt1(tuple.getValue0());
    }
    
    public <X0> Sextet<A,B,X0,C,D,E> addAt2(final Unit<X0> tuple) {
        return addAt2(tuple.getValue0());
    }
    
    public <X0> Sextet<A,B,C,X0,D,E> addAt3(final Unit<X0> tuple) {
        return addAt3(tuple.getValue0());
    }
    
    public <X0> Sextet<A,B,C,D,X0,E> addAt4(final Unit<X0> tuple) {
        return addAt4(tuple.getValue0());
    }
    
    public <X0> Sextet<A,B,C,D,E,X0> addAt5(final Unit<X0> tuple) {
        return addAt5(tuple.getValue0());
    }
    


    
    
    
    
    public <X0,X1> Septet<X0,X1,A,B,C,D,E> addAt0(final Pair<X0,X1> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Septet<A,X0,X1,B,C,D,E> addAt1(final Pair<X0,X1> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Septet<A,B,X0,X1,C,D,E> addAt2(final Pair<X0,X1> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Septet<A,B,C,X0,X1,D,E> addAt3(final Pair<X0,X1> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Septet<A,B,C,D,X0,X1,E> addAt4(final Pair<X0,X1> tuple) {
        return addAt4(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Septet<A,B,C,D,E,X0,X1> addAt5(final Pair<X0,X1> tuple) {
        return addAt5(tuple.getValue0(),tuple.getValue1());
    }

    
    

    
    
    
    
    public <X0,X1,X2> Octet<X0,X1,X2,A,B,C,D,E> addAt0(final Triplet<X0,X1,X2> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Octet<A,X0,X1,X2,B,C,D,E> addAt1(final Triplet<X0,X1,X2> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Octet<A,B,X0,X1,X2,C,D,E> addAt2(final Triplet<X0,X1,X2> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Octet<A,B,C,X0,X1,X2,D,E> addAt3(final Triplet<X0,X1,X2> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Octet<A,B,C,D,X0,X1,X2,E> addAt4(final Triplet<X0,X1,X2> tuple) {
        return addAt4(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Octet<A,B,C,D,E,X0,X1,X2> addAt5(final Triplet<X0,X1,X2> tuple) {
        return addAt5(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    
    


    
    
    
    
    public <X0,X1,X2,X3> Ennead<X0,X1,X2,X3,A,B,C,D,E> addAt0(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Ennead<A,X0,X1,X2,X3,B,C,D,E> addAt1(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Ennead<A,B,X0,X1,X2,X3,C,D,E> addAt2(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Ennead<A,B,C,X0,X1,X2,X3,D,E> addAt3(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Ennead<A,B,C,D,X0,X1,X2,X3,E> addAt4(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt4(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Ennead<A,B,C,D,E,X0,X1,X2,X3> addAt5(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt5(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4> Decade<X0,X1,X2,X3,X4,A,B,C,D,E> addAt0(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> Decade<A,X0,X1,X2,X3,X4,B,C,D,E> addAt1(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> Decade<A,B,X0,X1,X2,X3,X4,C,D,E> addAt2(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> Decade<A,B,C,X0,X1,X2,X3,X4,D,E> addAt3(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> Decade<A,B,C,D,X0,X1,X2,X3,X4,E> addAt4(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt4(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> Decade<A,B,C,D,E,X0,X1,X2,X3,X4> addAt5(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt5(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }

    
    
    
    
    
    public <X0> Sextet<A,B,C,D,E,X0> add(final X0 value0) {
        return addAt5(value0);
    }
    
    
    public <X0> Sextet<A,B,C,D,E,X0> add(final Unit<X0> tuple) {
        return addAt5(tuple);
    }
    
    
    
    
    public <X0,X1> Septet<A,B,C,D,E,X0,X1> add(final X0 value0, final X1 value1) {
        return addAt5(value0, value1);
    }
    
    
    public <X0,X1> Septet<A,B,C,D,E,X0,X1> add(final Pair<X0,X1> tuple) {
        return addAt5(tuple);
    }
    
    
    
    
    public <X0,X1,X2> Octet<A,B,C,D,E,X0,X1,X2> add(final X0 value0, final X1 value1, final X2 value2) {
        return addAt5(value0, value1, value2);
    }
    
    
    public <X0,X1,X2> Octet<A,B,C,D,E,X0,X1,X2> add(final Triplet<X0,X1,X2> tuple) {
        return addAt5(tuple);
    }
    
    
    
    
    public <X0,X1,X2,X3> Ennead<A,B,C,D,E,X0,X1,X2,X3> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return addAt5(value0, value1, value2, value3);
    }
    
    
    public <X0,X1,X2,X3> Ennead<A,B,C,D,E,X0,X1,X2,X3> add(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt5(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4> Decade<A,B,C,D,E,X0,X1,X2,X3,X4> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return addAt5(value0, value1, value2, value3, value4);
    }
    
    
    public <X0,X1,X2,X3,X4> Decade<A,B,C,D,E,X0,X1,X2,X3,X4> add(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt5(tuple);
    }
    
    
    
    
    
    
    public <X> Quintet<X,B,C,D,E> setAt0(final X value) {
        return new Quintet<X,B,C,D,E>(
                value, this.val1, this.val2, this.val3, this.val4);
    }
    
    public <X> Quintet<A,X,C,D,E> setAt1(final X value) {
        return new Quintet<A,X,C,D,E>(
                this.val0, value, this.val2, this.val3, this.val4);
    }
    
    public <X> Quintet<A,B,X,D,E> setAt2(final X value) {
        return new Quintet<A,B,X,D,E>(
                this.val0, this.val1, value, this.val3, this.val4);
    }
    
    public <X> Quintet<A,B,C,X,E> setAt3(final X value) {
        return new Quintet<A,B,C,X,E>(
                this.val0, this.val1, this.val2, value, this.val4);
    }
    
    public <X> Quintet<A,B,C,D,X> setAt4(final X value) {
        return new Quintet<A,B,C,D,X>(
                this.val0, this.val1, this.val2, this.val3, value);
    }
    
    
    
    
    
    
    
    
    public Quartet<B,C,D,E> removeFrom0() {
        return new Quartet<B,C,D,E>(
                this.val1, this.val2, this.val3, this.val4);
    }
    
    public Quartet<A,C,D,E> removeFrom1() {
        return new Quartet<A,C,D,E>(
                this.val0, this.val2, this.val3, this.val4);
    }
    
    public Quartet<A,B,D,E> removeFrom2() {
        return new Quartet<A,B,D,E>(
                this.val0, this.val1, this.val3, this.val4);
    }
    
    public Quartet<A,B,C,E> removeFrom3() {
        return new Quartet<A,B,C,E>(
                this.val0, this.val1, this.val2, this.val4);
    }
    
    public Quartet<A,B,C,D> removeFrom4() {
        return new Quartet<A,B,C,D>(
                this.val0, this.val1, this.val2, this.val3);
    }
    
    
    
}
