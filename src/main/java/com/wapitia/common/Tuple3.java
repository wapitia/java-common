package com.wapitia.common;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import lombok.Value;
import lombok.experimental.Accessors;

/** Generic triplet of objects.
 *
 *  @param <T1> Type of Triplet's first item.
 *  @param <T2> Type of Triplet's second item.
 *  @param <T3> Type of Triplet's third item.
 *  @see Collections
 */
@Value(staticConstructor = "of")
public class Tuple3<T1,T2,T3> {

    @Accessors(fluent=true, chain=false)
    private T1 first;

    @Accessors(fluent=true, chain=false)
    private T2 second;

    @Accessors(fluent=true, chain=false)
    private T3 third;


    /**
     * Get the index of the item at the 0-based index.
     *
     * @param <T> expected type of return value
     * @param index index must be 0 or 1.
     * @return first if index is 0, second if index is 1.
     * @throws IndexOutOfBoundsException when index is not in range (0..1)
     * @throws ClassCastException when the item object's type does not match
     *            the type {@code <T>} of the result
     */
    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        switch(index) {
            case 0: return (T) first;
            case 1: return (T) second;
            case 2: return (T) third;
            default:
                throw new IndexOutOfBoundsException("Tuple3.get");
        }
    }

    /**
     * Copy this tuple into a new tuple substituting one item at
     * a chosen index. The item must be an instance of that tuple slot's type.
     *
     * @param index index must be 0 or 1.
     * @param item substituting item
     * @return A new Tuple3 with one substitution
     * @throws IndexOutOfBoundsException when index is not in range (0..1)
     * @throws ClassCastException when the item object's type does not match
     *            the type of the slot it's replacing
     */
    @SuppressWarnings("unchecked")
    public Tuple3<T1,T2,T3> copy(int index, Object item) {
        switch(index) {
            case 0: return Tuple3.<T1,T2,T3> of((T1) item, second, third);
            case 1: return Tuple3.<T1,T2,T3> of(first, (T2) item, third);
            case 2: return Tuple3.<T1,T2,T3> of(first, second, (T3) item);
            default:
                throw new IndexOutOfBoundsException("Tuple3.copy");
        }
    }

    /**
     * Applies the values of this tuple to two consumer functions.
     * If a consumer function is null, it is ignored for its corresponding value.
     * @param afunc consumer of first
     * @param bfunc consumer of second
     * @param cfunc consumer of third
     */
    public void accept(Consumer<T1> afunc, Consumer<T2> bfunc, Consumer<T3> cfunc) {
        if (afunc != null)
            afunc.accept(first);
        if (bfunc != null)
            bfunc.accept(second);
        if (cfunc != null)
            cfunc.accept(third);
    }

    /**
     * Applies the values of this tuple to the consumers of another tuple.
     * If a consumer function is null, it is ignored for its corresponding value.
     * @param consums tuple of consumer functions
     */
    public void accept(Tuple3<Consumer<T1>, Consumer<T2>, Consumer<T3>> consums) {
        accept(consums.first(), consums.second(), consums.third());
    }

    /**
     * Applies the values of this tuple to two functions, returning their
     * results in a new result tuple.
     * If a function is null, it is ignored for its corresponding value, and
     * null is returned.
     * @param <R1> result type of first function
     * @param <R2> result type of second function
     * @param <R3> result type of third function
     * @param afunc function of first
     * @param bfunc function of second
     * @param cfunc function of third
     *
     * @return a new tuple with the results of applying the function pair.
     */
    public <R1,R2,R3> Tuple3<R1,R2,R3> apply(Function<T1,R1> afunc, Function<T2,R2> bfunc, Function<T3,R3> cfunc) {
        return Tuple3.of(
            Optional.ofNullable(afunc).map(af -> af.apply(first)).orElse(null),
            Optional.ofNullable(bfunc).map(bf -> bf.apply(second)).orElse(null),
            Optional.ofNullable(cfunc).map(cf -> cf.apply(third)).orElse(null) );
    }

    /**
     * Applies the values of this tuple to two functions, returning their
     * results in a new result tuple.
     * If a function is null, it is ignored for its corresponding value, and
     * null is returned.
     *
     * @param <R1> result type of first function
     * @param <R2> result type of second function
     * @param <R3> result type of third function
     * @param funcs pair of functions for first, second
     * @return a new tuple with the results of applying the function pair.
     */
    public <R1,R2,R3> Tuple3<R1,R2,R3> apply(Tuple3<Function<T1,R1>,Function<T2,R2>,Function<T3,R3>> funcs) {
        return apply(funcs.first(), funcs.second(), funcs.third());
    }

}