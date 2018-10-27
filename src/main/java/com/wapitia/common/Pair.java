package com.wapitia.common;

/** Generic pair of objects, useful to zip two streams or iterators.
 *
 *  @param <A> Type of Pair's first item.
 *  @param <B> Type of Pair's second item.
 *  @see Collections
 */
public class Pair<A,B> {
    private final A a;
    private final B b;

    /** Construct an immutable pair of objects of type {@code <A,B>}
     *
     *  @param a pair's first item.
     *  @param b pair's second item.
     */
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    /** Return the pair's first item.
     *
     *  @return value will be of type {@code A}.
     */
    public A _1() {
        return a;
    }

    /** Return the pair's second item.
     *
     *  @return value will be of type {@code B}.
     */
    public B _2() {
        return b;
    }
}