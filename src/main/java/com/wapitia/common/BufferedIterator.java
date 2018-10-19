package com.wapitia.common;

import java.util.Iterator;
import java.util.Optional;

/**
 * Buffers some iterator so that the next of the iterator
 * may be peeked at.
 *
 * @param <A> iterator container type
 */
public class BufferedIterator<A> implements Iterator<A> {

    private final Iterator<A> iter;

    // var
    private Optional<A> nextOpt;

    /** Wraps any iterator in order to buffer it so that the
     *  top can be peeked.
     *  @param iter iterator to buffer.
     */
    public BufferedIterator(Iterator<A> iter) {
        this.iter = iter;
        advanceNext();
    }

    /** Peel off the next item from the constructor iterator and cache it
     *  for hasNext, next, and peek.
     */
    void advanceNext() {
        this.nextOpt = iter.hasNext()
            ? Optional.of(iter.next())
            : Optional.empty();
    }

    @Override
    public boolean hasNext() {
        return nextOpt.isPresent();
    }

    @Override
    public A next() {
        A result = nextOpt.get();
        advanceNext();
        return result;
    }

    /** @return the optional next object, does not advance the iterator,
     *  can be repeatedly called.
     */
    public Optional<A> peek() {
        return nextOpt;
    }

}
