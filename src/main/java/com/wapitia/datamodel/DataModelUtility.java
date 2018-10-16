package com.wapitia.datamodel;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility class holds toString functions.
 */
public class DataModelUtility {

    public static class BufferedIterator<A> implements Iterator<A> {

        private final Iterator<A> iter;

        // var
        private Optional<A> nextOpt;

        public BufferedIterator(Iterator<A> iter) {
            this.iter = iter;
            advanceNext();
        }

        void advanceNext() {
            if (iter.hasNext()) {
                nextOpt = Optional.of(iter.next());
            }
            else {
                nextOpt = Optional.empty();
            }
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

        public Optional<A> peek() {
            return nextOpt;
        }

    }

    public static class Pair<A,B> {
        private final A a;
        private final B b;
        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public A _1() {
            return a;
        }

        public B _2() {
            return b;
        }
    }

    /** A zipped iterator of two iterators. */
    public static class PairIterator<A,B> implements Iterator<Pair<A,B>> {

        private final Iterator<A> aiter;
        private final Iterator<B> biter;

        public PairIterator(Iterator<A> aiter, Iterator<B> biter) {
            this.aiter = aiter;
            this.biter = biter;
        }

        @Override
        public boolean hasNext() {
            return aiter.hasNext() && biter.hasNext();
        }

        @Override
        public Pair<A, B> next() {
            return new Pair<A,B>(aiter.next(), biter.next());
        }

    }

    public static <A,B> Iterator<Pair<A,B>> zip(Iterator<A> iter1, Iterator<B> iter2) {
        Objects.requireNonNull(iter1);
        Objects.requireNonNull(iter2);

        return new PairIterator<A,B>(iter1, iter2);
    }

    public static <A,B> Stream<Pair<A,B>> zip(Stream<A> strm1, Stream<B> strm2) {
        Objects.requireNonNull(strm1);
        Objects.requireNonNull(strm2);

        Iterator<Pair<A,B>> iterator = zip(strm1.iterator(), strm2.iterator());

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator,
                Spliterator.ORDERED | Spliterator.IMMUTABLE | Spliterator.NONNULL), false);

    }

    public static void joinText(StringBuilder target, Stream<Consumer<StringBuilder>> inter, String sep) {
        joinText(target, inter, sep, "", "");
    }

    public static void joinText(StringBuilder target, Stream<Consumer<StringBuilder>> inter, String sep, String prefix, String suffix) {
        Objects.requireNonNull(target);
        Objects.requireNonNull(inter);
        Objects.requireNonNull(sep);
        Objects.requireNonNull(prefix);
        Objects.requireNonNull(suffix);
        target.append(prefix);
        Stream<Integer> is = IntStream.iterate(0, n -> n+1).boxed();
        Stream<Pair<Integer,Consumer<StringBuilder>>> strm = zip(is, inter);
        strm.forEach(ixConsumPair -> {
            if (ixConsumPair._1() > 0) {
                target.append(sep);
            }
            ixConsumPair._2().accept(target);
        });
        target.append(suffix);
    }

    public static void asEntity(StringBuilder target, String entityName, Consumer<StringBuilder> buildParams) {
        target.append(entityName).append("(");
        buildParams.accept(target);
        target.append(")");
    }

    public static <A> Stream<A> asStream(A ... vs) {
        return Arrays.stream(vs);
    }

    public static void asIdEntity(StringBuilder target, final boolean inclId, IdEntity idEntity, Consumer<StringBuilder> restParams) {

        asEntity(target, idEntity.getClass().getSimpleName(), (StringBuilder tgt) -> {
            if (inclId) {
                tgt.append(idEntity.getId()).append(", ");
            }
            restParams.accept(tgt);
        });
    }

    public static Consumer<StringBuilder> commaSepTextConsumer(Consumer<StringBuilder> ... bc) {
        return (StringBuilder target) -> joinText(target, asStream(bc), ",");
    }

    public static <V> Consumer<StringBuilder> fieldTextConsumer(String colName, V value) {
        return (StringBuilder bldr) ->
            bldr.append(colName).append("=").append(value == null ? "null" : value.toString());
    }

    @SafeVarargs
    public static void textCommaSep(StringBuilder target,
        Consumer<StringBuilder> ... bldrs)
    {
        commaSepTextConsumer(bldrs).accept(target);
    }

    public static void asAuditableEntity(final StringBuilder target,
        final AuditableEntity catEnt,
        boolean inclId, boolean inclAudibles)
    {
        final Consumer<StringBuilder> wrapper;
        if (inclAudibles) {
            wrapper = commaSepTextConsumer(catEnt::extraParams, catEnt::asAuditableParams);
        }
        else {
            wrapper = catEnt::extraParams;
        }

        asIdEntity(target, inclId, catEnt, wrapper);
    }

}
