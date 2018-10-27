package com.wapitia.common;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/** Offers zip support for two {@link Iterator}s or two {@link Streams}s. */
public class Streams {

    /** A zipped iterator of two iterators.
     *  Wraps and consumes two iterators
     *
     * @param <A> Item type of first iterator.
     * @param <B> Item type of second iterator.
     */
    public static class PairIterator<A,B> implements Iterator<Pair<A,B>> {

        private final Iterator<A> aiter;
        private final Iterator<B> biter;

        /** Constructor takes two iterators of type {@code <A>}
         *  and type {@code <B>}, respectively. Both must be non-null.
         *
         *  @param aiter First iterator with items of type {@code A}
         *  @param biter Second iterator with items of type {@code B}
         */
        public PairIterator(Iterator<A> aiter, Iterator<B> biter) {
            this.aiter = aiter;
            this.biter = biter;
        }

        /** Returns true only if stream {@code aiter} and stream
         *  {@code biter} are both populated.
         *  @returns true if both iterators are still populated
         */
        @Override
        public boolean hasNext() {
            return aiter.hasNext() && biter.hasNext();
        }

        /** Returns the next elements of stream {@code aiter} and stream
         *  {@code biter} as a {@link Pair} of items.
         *
         * @return a {@code Pair<A,B>} containing the next of both iterators.
         */
        @Override
        public Pair<A, B> next() {
            return new Pair<A,B>(aiter.next(), biter.next());
        }

    }

    /** Create and return an iterator which consumes the paired items of its two
     *  input iterators, where the resultant iterator produces {@link Pair}s
     *  of each item.
     *  The iterator finishes when either iterator is empty.
     *  If the iterators are not the same length, then the longest will
     *  still be able to produce the rest of its items.
     *
     *  @param aiter First iterator with items of type {@code A}
     *  @param biter Second iterator with items of type {@code B}
     *  @return a new Iterator consuming both input iterators
     */
    public static <A,B> Iterator<Pair<A,B>> zip(Iterator<A> aiter, Iterator<B> biter) {
        Objects.requireNonNull(aiter);
        Objects.requireNonNull(biter);

        return new PairIterator<A,B>(aiter, biter);
    }

    /** Create and return a stream which consumes the paired items of its two
     *  input streams, where the resultant stream produces {@link Pair}s
     *  of each item.
     *  The stream finishes when either stream is empty.
     *  If the streams are not the same length, then the longest will
     *  still be able to produce the rest of its items.
     *
     *  @param astrm First stream with items of type {@code A}
     *  @param bstrm Second stream with items of type {@code B}
     *  @return a new Stream consuming both input streams.
     */
    public static <A,B> Stream<Pair<A,B>> zip(Stream<A> astrm, Stream<B> bstrm) {
        Objects.requireNonNull(astrm);
        Objects.requireNonNull(bstrm);

        Iterator<Pair<A,B>> iterator = zip(astrm.iterator(), bstrm.iterator());

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator,
                Spliterator.ORDERED | Spliterator.IMMUTABLE | Spliterator.NONNULL),
            false);
    }
}
