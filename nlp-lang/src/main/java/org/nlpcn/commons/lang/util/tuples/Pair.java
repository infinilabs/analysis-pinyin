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

/**
 * <p>
 * A tuple of two elements.
 * </p> 
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public final class Pair<A,B> 
        extends Tuple
        implements IValue0<A>,
                   IValue1<B> {

    private static final long serialVersionUID = 2438099850625502138L;

    private static final int SIZE = 2;

    private final A val0;
    private final B val1;
    
    
    
    public static <A,B> Pair<A,B> with(final A value0, final B value1) {
        return new Pair<A,B>(value0,value1);
    }

    
    /**
     * <p>
     * Create tuple from array. Array has to have exactly two elements.
     * </p>
     * 
     * @param <X> the array component type 
     * @param array the array to be converted to a tuple
     * @return the tuple
     */
    public static <X> Pair<X,X> fromArray(final X[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (array.length != 2) {
            throw new IllegalArgumentException("Array must have exactly 2 elements in order to create a Pair. Size is " + array.length);
        }
        return new Pair<X,X>(array[0],array[1]);
    }

    
    /**
     * <p>
     * Create tuple from collection. Collection has to have exactly two elements.
     * </p>
     * 
     * @param <X> the collection component type 
     * @param collection the collection to be converted to a tuple
     * @return the tuple
     */
    public static <X> Pair<X,X> fromCollection(final Collection<X> collection) {
        return fromIterable(collection);
    }


    
    /**
     * <p>
     * Create tuple from iterable. Iterable has to have exactly two elements.
     * </p>
     * 
     * @param <X> the iterable component type 
     * @param iterable the iterable to be converted to a tuple
     * @return the tuple
     */
    public static <X> Pair<X,X> fromIterable(final Iterable<X> iterable) {
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
    public static <X> Pair<X,X> fromIterable(final Iterable<X> iterable, int index) {
        return fromIterable(iterable, index, false);
    }

    
    


    private static <X> Pair<X,X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {
        
        if (iterable == null) {
            throw new IllegalArgumentException("Iterable cannot be null");
        }

        boolean tooFewElements = false; 
        
        X element0 = null;
        X element1 = null;
        
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
        
        if (tooFewElements && exactSize) {
            throw new IllegalArgumentException("Not enough elements for creating a Pair (2 needed)");
        }
        
        if (iter.hasNext() && exactSize) {
            throw new IllegalArgumentException("Iterable must have exactly 2 available elements in order to create a Pair.");
        }
        
        return new Pair<X,X>(element0, element1);
        
    }
    
    
    
    
    
    public Pair(
            final A value0, 
            final B value1) {
        super(value0, value1);
        this.val0 = value0;
        this.val1 = value1;
    }


    public A getValue0() {
        return this.val0;
    }


    public B getValue1() {
        return this.val1;
    }


    @Override
    public int getSize() {
        return SIZE;
    }
    
    
    
    
    
    
    
    
    
    
    
    public <X0> Triplet<X0,A,B> addAt0(final X0 value0) {
        return new Triplet<X0,A,B>(
                value0, this.val0, this.val1);
    }
    
    public <X0> Triplet<A,X0,B> addAt1(final X0 value0) {
        return new Triplet<A,X0,B>(
                this.val0, value0, this.val1);
    }
    
    public <X0> Triplet<A,B,X0> addAt2(final X0 value0) {
        return new Triplet<A,B,X0>(
                this.val0, this.val1, value0);
    }

    
    
    
    
    public <X0,X1> Quartet<X0,X1,A,B> addAt0(final X0 value0, final X1 value1) {
        return new Quartet<X0,X1,A,B>(
                value0, value1, this.val0, this.val1);
    }
    
    public <X0,X1> Quartet<A,X0,X1,B> addAt1(final X0 value0, final X1 value1) {
        return new Quartet<A,X0,X1,B>(
                this.val0, value0, value1, this.val1);
    }
    
    public <X0,X1> Quartet<A,B,X0,X1> addAt2(final X0 value0, final X1 value1) {
        return new Quartet<A,B,X0,X1>(
                this.val0, this.val1, value0, value1);
    }
    


    
    
    
    
    public <X0,X1,X2> Quintet<X0,X1,X2,A,B> addAt0(final X0 value0, final X1 value1, final X2 value2) {
        return new Quintet<X0,X1,X2,A,B>(
                value0, value1, value2, this.val0, this.val1);
    }
    
    public <X0,X1,X2> Quintet<A,X0,X1,X2,B> addAt1(final X0 value0, final X1 value1, final X2 value2) {
        return new Quintet<A,X0,X1,X2,B>(
                this.val0, value0, value1, value2, this.val1);
    }
    
    public <X0,X1,X2> Quintet<A,B,X0,X1,X2> addAt2(final X0 value0, final X1 value1, final X2 value2) {
        return new Quintet<A,B,X0,X1,X2>(
                this.val0, this.val1, value0, value1, value2);
    }
    


    
    
    
    
    public <X0,X1,X2,X3> Sextet<X0,X1,X2,X3,A,B> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Sextet<X0,X1,X2,X3,A,B>(
                value0, value1, value2, value3, this.val0, this.val1);
    }
    
    public <X0,X1,X2,X3> Sextet<A,X0,X1,X2,X3,B> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Sextet<A,X0,X1,X2,X3,B>(
                this.val0, value0, value1, value2, value3, this.val1);
    }
    
    public <X0,X1,X2,X3> Sextet<A,B,X0,X1,X2,X3> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Sextet<A,B,X0,X1,X2,X3>(
                this.val0, this.val1, value0, value1, value2, value3);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4> Septet<X0,X1,X2,X3,X4,A,B> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Septet<X0,X1,X2,X3,X4,A,B>(
                value0, value1, value2, value3, value4, this.val0, this.val1);
    }
    
    public <X0,X1,X2,X3,X4> Septet<A,X0,X1,X2,X3,X4,B> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Septet<A,X0,X1,X2,X3,X4,B>(
                this.val0, value0, value1, value2, value3, value4, this.val1);
    }
    
    public <X0,X1,X2,X3,X4> Septet<A,B,X0,X1,X2,X3,X4> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Septet<A,B,X0,X1,X2,X3,X4>(
                this.val0, this.val1, value0, value1, value2, value3, value4);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4,X5> Octet<X0,X1,X2,X3,X4,X5,A,B> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new Octet<X0,X1,X2,X3,X4,X5,A,B>(
                value0, value1, value2, value3, value4, value5, this.val0, this.val1);
    }
    
    public <X0,X1,X2,X3,X4,X5> Octet<A,X0,X1,X2,X3,X4,X5,B> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new Octet<A,X0,X1,X2,X3,X4,X5,B>(
                this.val0, value0, value1, value2, value3, value4, value5, this.val1);
    }
    
    public <X0,X1,X2,X3,X4,X5> Octet<A,B,X0,X1,X2,X3,X4,X5> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new Octet<A,B,X0,X1,X2,X3,X4,X5>(
                this.val0, this.val1, value0, value1, value2, value3, value4, value5);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Ennead<X0,X1,X2,X3,X4,X5,X6,A,B> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new Ennead<X0,X1,X2,X3,X4,X5,X6,A,B>(
                value0, value1, value2, value3, value4, value5, value6, this.val0, this.val1);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Ennead<A,X0,X1,X2,X3,X4,X5,X6,B> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new Ennead<A,X0,X1,X2,X3,X4,X5,X6,B>(
                this.val0, value0, value1, value2, value3, value4, value5, value6, this.val1);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Ennead<A,B,X0,X1,X2,X3,X4,X5,X6> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new Ennead<A,B,X0,X1,X2,X3,X4,X5,X6>(
                this.val0, this.val1, value0, value1, value2, value3, value4, value5, value6);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Decade<X0,X1,X2,X3,X4,X5,X6,X7,A,B> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6, final X7 value7) {
        return new Decade<X0,X1,X2,X3,X4,X5,X6,X7,A,B>(
                value0, value1, value2, value3, value4, value5, value6, value7, this.val0, this.val1);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Decade<A,X0,X1,X2,X3,X4,X5,X6,X7,B> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6, final X7 value7) {
        return new Decade<A,X0,X1,X2,X3,X4,X5,X6,X7,B>(
                this.val0, value0, value1, value2, value3, value4, value5, value6, value7, this.val1);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Decade<A,B,X0,X1,X2,X3,X4,X5,X6,X7> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6, final X7 value7) {
        return new Decade<A,B,X0,X1,X2,X3,X4,X5,X6,X7>(
                this.val0, this.val1, value0, value1, value2, value3, value4, value5, value6, value7);
    }

    
    
    
    
    
    
    
    public <X0> Triplet<X0,A,B> addAt0(final Unit<X0> tuple) {
        return addAt0(tuple.getValue0());
    }
    
    public <X0> Triplet<A,X0,B> addAt1(final Unit<X0> tuple) {
        return addAt1(tuple.getValue0());
    }
    
    public <X0> Triplet<A,B,X0> addAt2(final Unit<X0> tuple) {
        return addAt2(tuple.getValue0());
    }
    


    
    
    
    
    public <X0,X1> Quartet<X0,X1,A,B> addAt0(final Pair<X0,X1> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Quartet<A,X0,X1,B> addAt1(final Pair<X0,X1> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Quartet<A,B,X0,X1> addAt2(final Pair<X0,X1> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1());
    }

    
    

    
    
    
    
    public <X0,X1,X2> Quintet<X0,X1,X2,A,B> addAt0(final Triplet<X0,X1,X2> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Quintet<A,X0,X1,X2,B> addAt1(final Triplet<X0,X1,X2> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Quintet<A,B,X0,X1,X2> addAt2(final Triplet<X0,X1,X2> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    
    


    
    
    
    
    public <X0,X1,X2,X3> Sextet<X0,X1,X2,X3,A,B> addAt0(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Sextet<A,X0,X1,X2,X3,B> addAt1(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Sextet<A,B,X0,X1,X2,X3> addAt2(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4> Septet<X0,X1,X2,X3,X4,A,B> addAt0(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> Septet<A,X0,X1,X2,X3,X4,B> addAt1(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> Septet<A,B,X0,X1,X2,X3,X4> addAt2(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4,X5> Octet<X0,X1,X2,X3,X4,X5,A,B> addAt0(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }
    
    public <X0,X1,X2,X3,X4,X5> Octet<A,X0,X1,X2,X3,X4,X5,B> addAt1(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }
    
    public <X0,X1,X2,X3,X4,X5> Octet<A,B,X0,X1,X2,X3,X4,X5> addAt2(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Ennead<X0,X1,X2,X3,X4,X5,X6,A,B> addAt0(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Ennead<A,X0,X1,X2,X3,X4,X5,X6,B> addAt1(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Ennead<A,B,X0,X1,X2,X3,X4,X5,X6> addAt2(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Decade<X0,X1,X2,X3,X4,X5,X6,X7,A,B> addAt0(final Octet<X0,X1,X2,X3,X4,X5,X6,X7> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6(),tuple.getValue7());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Decade<A,X0,X1,X2,X3,X4,X5,X6,X7,B> addAt1(final Octet<X0,X1,X2,X3,X4,X5,X6,X7> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6(),tuple.getValue7());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Decade<A,B,X0,X1,X2,X3,X4,X5,X6,X7> addAt2(final Octet<X0,X1,X2,X3,X4,X5,X6,X7> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6(),tuple.getValue7());
    }

    
    
    
    
    
    
    
    
    public <X0> Triplet<A,B,X0> add(final X0 value0) {
        return addAt2(value0);
    }
    
    
    public <X0> Triplet<A,B,X0> add(final Unit<X0> tuple) {
        return addAt2(tuple);
    }
    
    
    
    
    public <X0,X1> Quartet<A,B,X0,X1> add(final X0 value0, final X1 value1) {
        return addAt2(value0, value1);
    }
    
    
    public <X0,X1> Quartet<A,B,X0,X1> add(final Pair<X0,X1> tuple) {
        return addAt2(tuple);
    }
    
    
    
    
    public <X0,X1,X2> Quintet<A,B,X0,X1,X2> add(final X0 value0, final X1 value1, final X2 value2) {
        return addAt2(value0, value1, value2);
    }
    
    
    public <X0,X1,X2> Quintet<A,B,X0,X1,X2> add(final Triplet<X0,X1,X2> tuple) {
        return addAt2(tuple);
    }
    
    
    
    
    public <X0,X1,X2,X3> Sextet<A,B,X0,X1,X2,X3> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return addAt2(value0, value1, value2, value3);
    }
    
    
    public <X0,X1,X2,X3> Sextet<A,B,X0,X1,X2,X3> add(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt2(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4> Septet<A,B,X0,X1,X2,X3,X4> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return addAt2(value0, value1, value2, value3, value4);
    }
    
    
    public <X0,X1,X2,X3,X4> Septet<A,B,X0,X1,X2,X3,X4> add(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt2(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4,X5> Octet<A,B,X0,X1,X2,X3,X4,X5> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return addAt2(value0, value1, value2, value3, value4, value5);
    }
    
    
    public <X0,X1,X2,X3,X4,X5> Octet<A,B,X0,X1,X2,X3,X4,X5> add(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt2(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Ennead<A,B,X0,X1,X2,X3,X4,X5,X6> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return addAt2(value0, value1, value2, value3, value4, value5, value6);
    }
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Ennead<A,B,X0,X1,X2,X3,X4,X5,X6> add(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt2(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Decade<A,B,X0,X1,X2,X3,X4,X5,X6,X7> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6, final X7 value7) {
        return addAt2(value0, value1, value2, value3, value4, value5, value6, value7);
    }
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Decade<A,B,X0,X1,X2,X3,X4,X5,X6,X7> add(final Octet<X0,X1,X2,X3,X4,X5,X6,X7> tuple) {
        return addAt2(tuple);
    }
    
    
    
    
    
    
    
    
    
    public <X> Pair<X,B> setAt0(final X value) {
        return new Pair<X,B>(
                value, this.val1);
    }
    
    public <X> Pair<A,X> setAt1(final X value) {
        return new Pair<A,X>(
                this.val0, value);
    }
    
 
    
    
    
    
    
    
    
    public Unit<B> removeFrom0() {
        return new Unit<B>(
                this.val1);
    }
    
    public Unit<A> removeFrom1() {
        return new Unit<A>(
                this.val0);
    }
    
    
    
}
