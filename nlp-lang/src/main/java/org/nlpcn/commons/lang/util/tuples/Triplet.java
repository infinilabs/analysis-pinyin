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

/**
 * <p>
 * A tuple of three elements.
 * </p> 
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public final class Triplet<A,B,C> 
        extends Tuple
        implements IValue0<A>,
                   IValue1<B>,
                   IValue2<C> {

    private static final long serialVersionUID = -1877265551599483740L;

    private static final int SIZE = 3;

    private final A val0;
    private final B val1;
    private final C val2;
    
    
    
    public static <A,B,C> Triplet<A,B,C> with(final A value0, final B value1, final C value2) {
        return new Triplet<A,B,C>(value0,value1,value2);
    }

    
    /**
     * <p>
     * Create tuple from array. Array has to have exactly three elements.
     * </p>
     * 
     * @param <X> the array component type 
     * @param array the array to be converted to a tuple
     * @return the tuple
     */
    public static <X> Triplet<X,X,X> fromArray(final X[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (array.length != 3) {
            throw new IllegalArgumentException("Array must have exactly 3 elements in order to create a Triplet. Size is " + array.length);
        }
        return new Triplet<X,X,X>(array[0],array[1],array[2]);
    }

    
    /**
     * <p>
     * Create tuple from collection. Collection has to have exactly three elements.
     * </p>
     * 
     * @param <X> the collection component type 
     * @param collection the collection to be converted to a tuple
     * @return the tuple
     */
    public static <X> Triplet<X,X,X> fromCollection(final Collection<X> collection) {
        return fromIterable(collection);
    }


    
    /**
     * <p>
     * Create tuple from iterable. Iterable has to have exactly three elements.
     * </p>
     * 
     * @param <X> the iterable component type 
     * @param iterable the iterable to be converted to a tuple
     * @return the tuple
     */
    public static <X> Triplet<X,X,X> fromIterable(final Iterable<X> iterable) {
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
    public static <X> Triplet<X,X,X> fromIterable(final Iterable<X> iterable, int index) {
        return fromIterable(iterable, index, false);
    }

    


    private static <X> Triplet<X,X,X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {
        
        if (iterable == null) {
            throw new IllegalArgumentException("Iterable cannot be null");
        }

        boolean tooFewElements = false; 
        
        X element0 = null;
        X element1 = null;
        X element2 = null;
        
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
        
        if (tooFewElements && exactSize) {
            throw new IllegalArgumentException("Not enough elements for creating a Triplet (3 needed)");
        }
        
        if (iter.hasNext() && exactSize) {
            throw new IllegalArgumentException("Iterable must have exactly 3 available elements in order to create a Triplet.");
        }
        
        return new Triplet<X,X,X>(element0, element1, element2);
        
    }
    

    
    
    
    
    public Triplet(
            final A value0,
            final B value1,
            final C value2) {
        super(value0, value1, value2);
        this.val0 = value0;
        this.val1 = value1;
        this.val2 = value2;
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


    @Override
    public int getSize() {
        return SIZE;
    }
    
    
    
    
    
    
    
    
    
    public <X0> Quartet<X0,A,B,C> addAt0(final X0 value0) {
        return new Quartet<X0,A,B,C>(
                value0, this.val0, this.val1, this.val2);
    }
    
    public <X0> Quartet<A,X0,B,C> addAt1(final X0 value0) {
        return new Quartet<A,X0,B,C>(
                this.val0, value0, this.val1, this.val2);
    }
    
    public <X0> Quartet<A,B,X0,C> addAt2(final X0 value0) {
        return new Quartet<A,B,X0,C>(
                this.val0, this.val1, value0, this.val2);
    }
    
    public <X0> Quartet<A,B,C,X0> addAt3(final X0 value0) {
        return new Quartet<A,B,C,X0>(
                this.val0, this.val1, this.val2, value0);
    }

    
    
    
    
    public <X0,X1> Quintet<X0,X1,A,B,C> addAt0(final X0 value0, final X1 value1) {
        return new Quintet<X0,X1,A,B,C>(
                value0, value1, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1> Quintet<A,X0,X1,B,C> addAt1(final X0 value0, final X1 value1) {
        return new Quintet<A,X0,X1,B,C>(
                this.val0, value0, value1, this.val1, this.val2);
    }
    
    public <X0,X1> Quintet<A,B,X0,X1,C> addAt2(final X0 value0, final X1 value1) {
        return new Quintet<A,B,X0,X1,C>(
                this.val0, this.val1, value0, value1, this.val2);
    }
    
    public <X0,X1> Quintet<A,B,C,X0,X1> addAt3(final X0 value0, final X1 value1) {
        return new Quintet<A,B,C,X0,X1>(
                this.val0, this.val1, this.val2, value0, value1);
    }
    


    
    
    
    
    public <X0,X1,X2> Sextet<X0,X1,X2,A,B,C> addAt0(final X0 value0, final X1 value1, final X2 value2) {
        return new Sextet<X0,X1,X2,A,B,C>(
                value0, value1, value2, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1,X2> Sextet<A,X0,X1,X2,B,C> addAt1(final X0 value0, final X1 value1, final X2 value2) {
        return new Sextet<A,X0,X1,X2,B,C>(
                this.val0, value0, value1, value2, this.val1, this.val2);
    }
    
    public <X0,X1,X2> Sextet<A,B,X0,X1,X2,C> addAt2(final X0 value0, final X1 value1, final X2 value2) {
        return new Sextet<A,B,X0,X1,X2,C>(
                this.val0, this.val1, value0, value1, value2, this.val2);
    }
    
    public <X0,X1,X2> Sextet<A,B,C,X0,X1,X2> addAt3(final X0 value0, final X1 value1, final X2 value2) {
        return new Sextet<A,B,C,X0,X1,X2>(
                this.val0, this.val1, this.val2, value0, value1, value2);
    }
    


    
    
    
    
    public <X0,X1,X2,X3> Septet<X0,X1,X2,X3,A,B,C> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Septet<X0,X1,X2,X3,A,B,C>(
                value0, value1, value2, value3, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3> Septet<A,X0,X1,X2,X3,B,C> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Septet<A,X0,X1,X2,X3,B,C>(
                this.val0, value0, value1, value2, value3, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3> Septet<A,B,X0,X1,X2,X3,C> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Septet<A,B,X0,X1,X2,X3,C>(
                this.val0, this.val1, value0, value1, value2, value3, this.val2);
    }
    
    public <X0,X1,X2,X3> Septet<A,B,C,X0,X1,X2,X3> addAt3(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Septet<A,B,C,X0,X1,X2,X3>(
                this.val0, this.val1, this.val2, value0, value1, value2, value3);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4> Octet<X0,X1,X2,X3,X4,A,B,C> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Octet<X0,X1,X2,X3,X4,A,B,C>(
                value0, value1, value2, value3, value4, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4> Octet<A,X0,X1,X2,X3,X4,B,C> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Octet<A,X0,X1,X2,X3,X4,B,C>(
                this.val0, value0, value1, value2, value3, value4, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4> Octet<A,B,X0,X1,X2,X3,X4,C> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Octet<A,B,X0,X1,X2,X3,X4,C>(
                this.val0, this.val1, value0, value1, value2, value3, value4, this.val2);
    }
    
    public <X0,X1,X2,X3,X4> Octet<A,B,C,X0,X1,X2,X3,X4> addAt3(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Octet<A,B,C,X0,X1,X2,X3,X4>(
                this.val0, this.val1, this.val2, value0, value1, value2, value3, value4);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4,X5> Ennead<X0,X1,X2,X3,X4,X5,A,B,C> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new Ennead<X0,X1,X2,X3,X4,X5,A,B,C>(
                value0, value1, value2, value3, value4, value5, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5> Ennead<A,X0,X1,X2,X3,X4,X5,B,C> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new Ennead<A,X0,X1,X2,X3,X4,X5,B,C>(
                this.val0, value0, value1, value2, value3, value4, value5, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5> Ennead<A,B,X0,X1,X2,X3,X4,X5,C> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new Ennead<A,B,X0,X1,X2,X3,X4,X5,C>(
                this.val0, this.val1, value0, value1, value2, value3, value4, value5, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5> Ennead<A,B,C,X0,X1,X2,X3,X4,X5> addAt3(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new Ennead<A,B,C,X0,X1,X2,X3,X4,X5>(
                this.val0, this.val1, this.val2, value0, value1, value2, value3, value4, value5);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Decade<X0,X1,X2,X3,X4,X5,X6,A,B,C> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new Decade<X0,X1,X2,X3,X4,X5,X6,A,B,C>(
                value0, value1, value2, value3, value4, value5, value6, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Decade<A,X0,X1,X2,X3,X4,X5,X6,B,C> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new Decade<A,X0,X1,X2,X3,X4,X5,X6,B,C>(
                this.val0, value0, value1, value2, value3, value4, value5, value6, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Decade<A,B,X0,X1,X2,X3,X4,X5,X6,C> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new Decade<A,B,X0,X1,X2,X3,X4,X5,X6,C>(
                this.val0, this.val1, value0, value1, value2, value3, value4, value5, value6, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Decade<A,B,C,X0,X1,X2,X3,X4,X5,X6> addAt3(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new Decade<A,B,C,X0,X1,X2,X3,X4,X5,X6>(
                this.val0, this.val1, this.val2, value0, value1, value2, value3, value4, value5, value6);
    }

    
    
    
    
    
    
    public <X0> Quartet<X0,A,B,C> addAt0(final Unit<X0> tuple) {
        return addAt0(tuple.getValue0());
    }
    
    public <X0> Quartet<A,X0,B,C> addAt1(final Unit<X0> tuple) {
        return addAt1(tuple.getValue0());
    }
    
    public <X0> Quartet<A,B,X0,C> addAt2(final Unit<X0> tuple) {
        return addAt2(tuple.getValue0());
    }
    
    public <X0> Quartet<A,B,C,X0> addAt3(final Unit<X0> tuple) {
        return addAt3(tuple.getValue0());
    }
    


    
    
    
    
    public <X0,X1> Quintet<X0,X1,A,B,C> addAt0(final Pair<X0,X1> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Quintet<A,X0,X1,B,C> addAt1(final Pair<X0,X1> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Quintet<A,B,X0,X1,C> addAt2(final Pair<X0,X1> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Quintet<A,B,C,X0,X1> addAt3(final Pair<X0,X1> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1());
    }

    
    

    
    
    
    
    public <X0,X1,X2> Sextet<X0,X1,X2,A,B,C> addAt0(final Triplet<X0,X1,X2> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Sextet<A,X0,X1,X2,B,C> addAt1(final Triplet<X0,X1,X2> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Sextet<A,B,X0,X1,X2,C> addAt2(final Triplet<X0,X1,X2> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Sextet<A,B,C,X0,X1,X2> addAt3(final Triplet<X0,X1,X2> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    
    


    
    
    
    
    public <X0,X1,X2,X3> Septet<X0,X1,X2,X3,A,B,C> addAt0(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Septet<A,X0,X1,X2,X3,B,C> addAt1(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Septet<A,B,X0,X1,X2,X3,C> addAt2(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Septet<A,B,C,X0,X1,X2,X3> addAt3(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4> Octet<X0,X1,X2,X3,X4,A,B,C> addAt0(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> Octet<A,X0,X1,X2,X3,X4,B,C> addAt1(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> Octet<A,B,X0,X1,X2,X3,X4,C> addAt2(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> Octet<A,B,C,X0,X1,X2,X3,X4> addAt3(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4,X5> Ennead<X0,X1,X2,X3,X4,X5,A,B,C> addAt0(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }
    
    public <X0,X1,X2,X3,X4,X5> Ennead<A,X0,X1,X2,X3,X4,X5,B,C> addAt1(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }
    
    public <X0,X1,X2,X3,X4,X5> Ennead<A,B,X0,X1,X2,X3,X4,X5,C> addAt2(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }
    
    public <X0,X1,X2,X3,X4,X5> Ennead<A,B,C,X0,X1,X2,X3,X4,X5> addAt3(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Decade<X0,X1,X2,X3,X4,X5,X6,A,B,C> addAt0(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Decade<A,X0,X1,X2,X3,X4,X5,X6,B,C> addAt1(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Decade<A,B,X0,X1,X2,X3,X4,X5,X6,C> addAt2(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Decade<A,B,C,X0,X1,X2,X3,X4,X5,X6> addAt3(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }

    
    
    
    
    
    public <X0> Quartet<A,B,C,X0> add(final X0 value0) {
        return addAt3(value0);
    }
    
    
    public <X0> Quartet<A,B,C,X0> add(final Unit<X0> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    public <X0,X1> Quintet<A,B,C,X0,X1> add(final X0 value0, final X1 value1) {
        return addAt3(value0, value1);
    }
    
    
    public <X0,X1> Quintet<A,B,C,X0,X1> add(final Pair<X0,X1> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    public <X0,X1,X2> Sextet<A,B,C,X0,X1,X2> add(final X0 value0, final X1 value1, final X2 value2) {
        return addAt3(value0, value1, value2);
    }
    
    
    public <X0,X1,X2> Sextet<A,B,C,X0,X1,X2> add(final Triplet<X0,X1,X2> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    public <X0,X1,X2,X3> Septet<A,B,C,X0,X1,X2,X3> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return addAt3(value0, value1, value2, value3);
    }
    
    
    public <X0,X1,X2,X3> Septet<A,B,C,X0,X1,X2,X3> add(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4> Octet<A,B,C,X0,X1,X2,X3,X4> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return addAt3(value0, value1, value2, value3, value4);
    }
    
    
    public <X0,X1,X2,X3,X4> Octet<A,B,C,X0,X1,X2,X3,X4> add(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4,X5> Ennead<A,B,C,X0,X1,X2,X3,X4,X5> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return addAt3(value0, value1, value2, value3, value4, value5);
    }
    
    
    public <X0,X1,X2,X3,X4,X5> Ennead<A,B,C,X0,X1,X2,X3,X4,X5> add(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Decade<A,B,C,X0,X1,X2,X3,X4,X5,X6> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return addAt3(value0, value1, value2, value3, value4, value5, value6);
    }
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Decade<A,B,C,X0,X1,X2,X3,X4,X5,X6> add(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt3(tuple);
    }

    
    
    
    
    
    
    
    public <X> Triplet<X,B,C> setAt0(final X value) {
        return new Triplet<X,B,C>(
                value, this.val1, this.val2);
    }
    
    public <X> Triplet<A,X,C> setAt1(final X value) {
        return new Triplet<A,X,C>(
                this.val0, value, this.val2);
    }
    
    public <X> Triplet<A,B,X> setAt2(final X value) {
        return new Triplet<A,B,X>(
                this.val0, this.val1, value);
    }
    
    
    
    
    
    
    
    public Pair<B,C> removeFrom0() {
        return new Pair<B,C>(
                this.val1, this.val2);
    }
    
    public Pair<A,C> removeFrom1() {
        return new Pair<A,C>(
                this.val0, this.val2);
    }
    
    public Pair<A,B> removeFrom2() {
        return new Pair<A,B>(
                this.val0, this.val1);
    }
    
    
    
}
