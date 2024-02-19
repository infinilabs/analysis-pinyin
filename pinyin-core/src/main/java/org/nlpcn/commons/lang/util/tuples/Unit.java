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

/**
 * <p>
 * A tuple of one element.
 * </p> 
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public final class Unit<A> 
        extends Tuple
        implements IValue0<A> {

    private static final long serialVersionUID = -9113114724069537096L;

    private static final int SIZE = 1;
    
    private final A val0;
    
    
    public static <A> Unit<A> with(final A value0) {
        return new Unit<A>(value0);
    }

    
    /**
     * <p>
     * Create tuple from array. Array has to have exactly one element.
     * </p>
     * 
     * @param <X> the array component type 
     * @param array the array to be converted to a tuple
     * @return the tuple
     */
    public static <X> Unit<X> fromArray(final X[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (array.length != 1) {
            throw new IllegalArgumentException("Array must have exactly 1 element in order to create a Unit. Size is " + array.length);
        }
        return new Unit<X>(array[0]);
    }


    /**
     * <p>
     * Create tuple from collection. Collection has to have exactly one element.
     * </p>
     * 
     * @param <X> the collection component type 
     * @param collection the collection to be converted to a tuple
     * @return the tuple
     */
    public static <X> Unit<X> fromCollection(final Collection<X> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection cannot be null");
        }
        if (collection.size() != 1) {
            throw new IllegalArgumentException("Collection must have exactly 1 element in order to create a Unit. Size is " + collection.size());
        }
        final Iterator<X> iter = collection.iterator();
        return new Unit<X>(iter.next());
    }
    

    
    /**
     * <p>
     * Create tuple from iterable. Iterable has to have exactly one element.
     * </p>
     * 
     * @param <X> the iterable component type 
     * @param iterable the iterable to be converted to a tuple
     * @return the tuple
     */
    public static <X> Unit<X> fromIterable(final Iterable<X> iterable) {
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
    public static <X> Unit<X> fromIterable(final Iterable<X> iterable, int index) {
        return fromIterable(iterable, index, false);
    }
    

    


    private static <X> Unit<X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {
        
        if (iterable == null) {
            throw new IllegalArgumentException("Iterable cannot be null");
        }

        boolean tooFewElements = false; 
        
        X element0 = null;
        
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
        
        if (tooFewElements && exactSize) {
            throw new IllegalArgumentException("Not enough elements for creating a Unit (1 needed)");
        }
        
        if (iter.hasNext() && exactSize) {
            throw new IllegalArgumentException("Iterable must have exactly 1 available element in order to create a Unit.");
        }
        
        return new Unit<X>(element0);
        
    }
    
    

    
    public Unit(final A value0) {
        super(value0);
        this.val0 = value0;
    }
    
    
    public A getValue0() {
        return this.val0;
    }


    @Override
    public int getSize() {
        return SIZE;
    }
    
    
    
    
    
    
    
    
    
    
    
    public <X0> Pair<X0,A> addAt0(final X0 value0) {
        return new Pair<X0,A>(
                value0, this.val0);
    }
    
    public <X0> Pair<A,X0> addAt1(final X0 value0) {
        return new Pair<A,X0>(
                this.val0, value0);
    }

    
    
    
    
    public <X0,X1> Triplet<X0,X1,A> addAt0(final X0 value0, final X1 value1) {
        return new Triplet<X0,X1,A>(
                value0, value1, this.val0);
    }
    
    public <X0,X1> Triplet<A,X0,X1> addAt1(final X0 value0, final X1 value1) {
        return new Triplet<A,X0,X1>(
                this.val0, value0, value1);
    }
    


    
    
    
    
    public <X0,X1,X2> Quartet<X0,X1,X2,A> addAt0(final X0 value0, final X1 value1, final X2 value2) {
        return new Quartet<X0,X1,X2,A>(
                value0, value1, value2, this.val0);
    }
    
    public <X0,X1,X2> Quartet<A,X0,X1,X2> addAt1(final X0 value0, final X1 value1, final X2 value2) {
        return new Quartet<A,X0,X1,X2>(
                this.val0, value0, value1, value2);
    }
    


    
    
    
    
    public <X0,X1,X2,X3> Quintet<X0,X1,X2,X3,A> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Quintet<X0,X1,X2,X3,A>(
                value0, value1, value2, value3, this.val0);
    }
    
    public <X0,X1,X2,X3> Quintet<A,X0,X1,X2,X3> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new Quintet<A,X0,X1,X2,X3>(
                this.val0, value0, value1, value2, value3);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4> Sextet<X0,X1,X2,X3,X4,A> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Sextet<X0,X1,X2,X3,X4,A>(
                value0, value1, value2, value3, value4, this.val0);
    }
    
    public <X0,X1,X2,X3,X4> Sextet<A,X0,X1,X2,X3,X4> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new Sextet<A,X0,X1,X2,X3,X4>(
                this.val0, value0, value1, value2, value3, value4);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4,X5> Septet<X0,X1,X2,X3,X4,X5,A> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new Septet<X0,X1,X2,X3,X4,X5,A>(
                value0, value1, value2, value3, value4, value5, this.val0);
    }
    
    public <X0,X1,X2,X3,X4,X5> Septet<A,X0,X1,X2,X3,X4,X5> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new Septet<A,X0,X1,X2,X3,X4,X5>(
                this.val0, value0, value1, value2, value3, value4, value5);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Octet<X0,X1,X2,X3,X4,X5,X6,A> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new Octet<X0,X1,X2,X3,X4,X5,X6,A>(
                value0, value1, value2, value3, value4, value5, value6, this.val0);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Octet<A,X0,X1,X2,X3,X4,X5,X6> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new Octet<A,X0,X1,X2,X3,X4,X5,X6>(
                this.val0, value0, value1, value2, value3, value4, value5, value6);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Ennead<X0,X1,X2,X3,X4,X5,X6,X7,A> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6, final X7 value7) {
        return new Ennead<X0,X1,X2,X3,X4,X5,X6,X7,A>(
                value0, value1, value2, value3, value4, value5, value6, value7, this.val0);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Ennead<A,X0,X1,X2,X3,X4,X5,X6,X7> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6, final X7 value7) {
        return new Ennead<A,X0,X1,X2,X3,X4,X5,X6,X7>(
                this.val0, value0, value1, value2, value3, value4, value5, value6, value7);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7,X8> Decade<X0,X1,X2,X3,X4,X5,X6,X7,X8,A> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6, final X7 value7, final X8 value8) {
        return new Decade<X0,X1,X2,X3,X4,X5,X6,X7,X8,A>(
                value0, value1, value2, value3, value4, value5, value6, value7, value8, this.val0);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6,X7,X8> Decade<A,X0,X1,X2,X3,X4,X5,X6,X7,X8> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6, final X7 value7, final X8 value8) {
        return new Decade<A,X0,X1,X2,X3,X4,X5,X6,X7,X8>(
                this.val0, value0, value1, value2, value3, value4, value5, value6, value7, value8);
    }

    
    
    
    
    
    
    
    public <X0> Pair<X0,A> addAt0(final Unit<X0> tuple) {
        return addAt0(tuple.getValue0());
    }
    
    public <X0> Pair<A,X0> addAt1(final Unit<X0> tuple) {
        return addAt1(tuple.getValue0());
    }
    


    
    
    
    
    public <X0,X1> Triplet<X0,X1,A> addAt0(final Pair<X0,X1> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> Triplet<A,X0,X1> addAt1(final Pair<X0,X1> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1());
    }

    
    

    
    
    
    
    public <X0,X1,X2> Quartet<X0,X1,X2,A> addAt0(final Triplet<X0,X1,X2> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> Quartet<A,X0,X1,X2> addAt1(final Triplet<X0,X1,X2> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    
    


    
    
    
    
    public <X0,X1,X2,X3> Quintet<X0,X1,X2,X3,A> addAt0(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> Quintet<A,X0,X1,X2,X3> addAt1(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4> Sextet<X0,X1,X2,X3,X4,A> addAt0(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> Sextet<A,X0,X1,X2,X3,X4> addAt1(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4,X5> Septet<X0,X1,X2,X3,X4,X5,A> addAt0(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }
    
    public <X0,X1,X2,X3,X4,X5> Septet<A,X0,X1,X2,X3,X4,X5> addAt1(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }
    
    
    

    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Octet<X0,X1,X2,X3,X4,X5,X6,A> addAt0(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> Octet<A,X0,X1,X2,X3,X4,X5,X6> addAt1(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Ennead<X0,X1,X2,X3,X4,X5,X6,X7,A> addAt0(final Octet<X0,X1,X2,X3,X4,X5,X6,X7> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6(),tuple.getValue7());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Ennead<A,X0,X1,X2,X3,X4,X5,X6,X7> addAt1(final Octet<X0,X1,X2,X3,X4,X5,X6,X7> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6(),tuple.getValue7());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7,X8> Decade<X0,X1,X2,X3,X4,X5,X6,X7,X8,A> addAt0(final Ennead<X0,X1,X2,X3,X4,X5,X6,X7,X8> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6(),tuple.getValue7(),tuple.getValue8());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6,X7,X8> Decade<A,X0,X1,X2,X3,X4,X5,X6,X7,X8> addAt1(final Ennead<X0,X1,X2,X3,X4,X5,X6,X7,X8> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6(),tuple.getValue7(),tuple.getValue8());
    }

    
    
    
    
    
    
    
    
    public <X0> Pair<A,X0> add(final X0 value0) {
        return addAt1(value0);
    }
    
    
    public <X0> Pair<A,X0> add(final Unit<X0> tuple) {
        return addAt1(tuple);
    }
    
    
    
    
    public <X0,X1> Triplet<A,X0,X1> add(final X0 value0, final X1 value1) {
        return addAt1(value0, value1);
    }
    
    
    public <X0,X1> Triplet<A,X0,X1> add(final Pair<X0,X1> tuple) {
        return addAt1(tuple);
    }
    
    
    
    
    public <X0,X1,X2> Quartet<A,X0,X1,X2> add(final X0 value0, final X1 value1, final X2 value2) {
        return addAt1(value0, value1, value2);
    }
    
    
    public <X0,X1,X2> Quartet<A,X0,X1,X2> add(final Triplet<X0,X1,X2> tuple) {
        return addAt1(tuple);
    }
    
    
    
    
    public <X0,X1,X2,X3> Quintet<A,X0,X1,X2,X3> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return addAt1(value0, value1, value2, value3);
    }
    
    
    public <X0,X1,X2,X3> Quintet<A,X0,X1,X2,X3> add(final Quartet<X0,X1,X2,X3> tuple) {
        return addAt1(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4> Sextet<A,X0,X1,X2,X3,X4> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return addAt1(value0, value1, value2, value3, value4);
    }
    
    
    public <X0,X1,X2,X3,X4> Sextet<A,X0,X1,X2,X3,X4> add(final Quintet<X0,X1,X2,X3,X4> tuple) {
        return addAt1(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4,X5> Septet<A,X0,X1,X2,X3,X4,X5> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return addAt1(value0, value1, value2, value3, value4, value5);
    }
    
    
    public <X0,X1,X2,X3,X4,X5> Septet<A,X0,X1,X2,X3,X4,X5> add(final Sextet<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt1(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Octet<A,X0,X1,X2,X3,X4,X5,X6> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return addAt1(value0, value1, value2, value3, value4, value5, value6);
    }
    
    
    public <X0,X1,X2,X3,X4,X5,X6> Octet<A,X0,X1,X2,X3,X4,X5,X6> add(final Septet<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt1(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Ennead<A,X0,X1,X2,X3,X4,X5,X6,X7> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6, final X7 value7) {
        return addAt1(value0, value1, value2, value3, value4, value5, value6, value7);
    }
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7> Ennead<A,X0,X1,X2,X3,X4,X5,X6,X7> add(final Octet<X0,X1,X2,X3,X4,X5,X6,X7> tuple) {
        return addAt1(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7,X8> Decade<A,X0,X1,X2,X3,X4,X5,X6,X7,X8> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6, final X7 value7, final X8 value8) {
        return addAt1(value0, value1, value2, value3, value4, value5, value6, value7, value8);
    }
    
    
    public <X0,X1,X2,X3,X4,X5,X6,X7,X8> Decade<A,X0,X1,X2,X3,X4,X5,X6,X7,X8> add(final Ennead<X0,X1,X2,X3,X4,X5,X6,X7,X8> tuple) {
        return addAt1(tuple);
    }
    
    
    
    
    
    
    
    
    
    
    public <X> Unit<X> setAt0(final X value) {
        return new Unit<X>(
                value);
    }
    
    
    
}
