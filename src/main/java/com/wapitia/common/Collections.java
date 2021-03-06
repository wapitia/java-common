package com.wapitia.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/** Extensions to java's collection utilities. */
public class Collections {

    /**
     * list.Map() functionality. Needed for java 8 and while future java
     * versions lack.
     *
     * @param list List of some source type
     * @param map map function
     * @return a List of target types
     */
    public static <S,T> List<T> listMap(Collection<S> list, Function<S,T> map) {
        final List<T> result = list.stream().map(map).collect(Collectors.toList());
        return result;
    }

    /**
     * Add a new list item to a list in a map for a particular key. The map is of
     * the form {@code Map<K,List<V>>} for keys of type `K` and values of type `V`. The
     * given value is appended to the list in the map for a given key K. If the
     * entry does not yet exist, a new ArrayList is created for that slot and value
     * becomes the first and only entry in the new list.
     *
     * @param map target for addition
     * @param key map slot in which to add
     * @param value value to add to list value at key
     */
    public static <K, V> void addToMapOfLists(Map<K, List<V>> map, K key, V value) {
        addToMapOfLists(map, key, value, (K k) -> new ArrayList<>());
    }

    /**
     * Add a new list item to a list in a map for a particular key. The map is of
     * the form {@code Map<K,List<V>>} for keys of type `K` and values of type `V`. The
     * given value is appended to the list in the map for a given key K. If the
     * entry does not yet exist, the given listMaker supplier of Lists will be
     * invoked to create a new list for that slot and the value becomes the first
     * and only entry in the new list.
     *
     * @param map target for addition
     * @param key map slot in which to add
     * @param value value to add to list value at key
     * @param listMaker maker of lists for new map values
     */
    public static <K, V> void addToMapOfLists(Map<K, List<V>> map, K key, V value,
            Function<K, List<V>> listMaker) {
        getOrCreateMapValue(map, key, listMaker).add(value);
    }

    /**
     * Get or create the value from the given map, or create and install a new one
     * if it doesn't yet exist.
     *
     * @param                      <K> Map's key type
     * @param                      <C> Map's value type
     *
     * @param map                  the source map from which to obtain the value or
     *                             in which to add a newly created value.
     * @param key                  the key to the entry in the map from which to
     *                             return its value.
     * @param defaultValueProvider function taking a key to generate and return a
     *                             default value when there is not a pre-existing
     *                             value at key in the map.
     * @return the map value at that key or whats provided by the
     *             defaultValueProvider
     */
    public static <K, C> C getOrCreateMapValue(Map<K, C> map, K key,
            Function<K, C> defaultValueProvider) {
        return Optional.ofNullable(map.get(key))
            .orElseGet(new MapUpdater<>(map, key, defaultValueProvider));
    }

    /**
     * Supplier of a default Map Value, having the side effect of creating and
     * replacing an entry in a Map.
     *
     * @param <K> Map's key type
     * @param <C> Map's value type
     */
    public static class MapUpdater<K, C> implements Supplier<C> {

        final Map<K, C> map;
        final K key;
        final Function<K, C> valueProvider;

        /**
         * @param map           the source map from which to obtain the value or
         *                      in which to add a newly created value.
         * @param key           the key to the entry in the map from which to
         *                      return its value.
         * @param valueProvider function taking a key to generate and return a
         *                      default value when there is not a pre-existing
         *                      value at key in the map.
         */
        MapUpdater(Map<K, C> map, K key, Function<K, C> valueProvider) {
            this.map = map;
            this.key = key;
            this.valueProvider = valueProvider;
        }

        /**
         * Create a new value from the defaultValueProvider, add it to the map at the
         * given key, and return that new value.
         */
        @Override
        public C get() {
            final C newItem = valueProvider.apply(key);
            map.put(key, newItem);
            return newItem;
        }

    }

    /** Get the optional first element of the chosen list, wrapped
     *  as an Optional. Does not complain if there are multiple elements
     *  in the list.
     *
     *  <p>Functional definition:
     *  <pre>{@code
     *    def optionalOfSingle<A>(a: List) = a match {
     *      case h :: _ => Some(h)
     *      case _      => None
     *    }
     *  }</pre>
     *
     *  @param list Collection from which to pull the first element. must not be null.
     *  @return Some first element of list or None if list is empty.
     */
    public static <A> Optional<A> firstOfList(List<A> list) {
        final Optional<A> result;
        if (list.isEmpty())
            result = Optional.empty();
        else
            result = Optional.of(list.get(0));
        return result;
    }

    /** Get the optional first element of the chosen list, wrapped
     *  as an Optional. Throws a RuntimeException if there are multiple
     *  items in the list.
     *
     *  <p>Functional definition:
     *  <pre>{@code
     *    def optionalOfSingle<A>(a: List) = a match {
     *      case _ :: _ :: _ => throw new RuntimeException("...")
     *      case h ::       _ => Some(h)
     *      case _            => None
     *    }
     *  }</pre>
     *
     *  @param list Collection from which to pull the first element. must not be null.
     *  @return Some first element of list or None if list is empty.
     *  @throws RuntimeException if there are multiple items in the list
     */
    public static <A> Optional<A> asSingleton(List<A> list) {
        final Optional<A> result;
        if (list.isEmpty())
            result = Optional.empty();
        else if (list.size() == 1)
            result = Optional.of(list.get(0));
        else
            throw new RuntimeException("List has multiple elements.");
        return result;
    }


    /** Get or make a new item by the getter, creating a new one if
     *  getter returns null, and setting the value via the setter if created.
     *
     *  @param getter Supplier of a collection, which may return null
     *  @param setter called only if getter returns null and creator returns
     *              a new collection. May be null, in which case setting is ignored.
     *  @param creator called only when getter returns null.
     *  @return The made item
     */
    public static <T> T getOrMake(Supplier<T> getter, Consumer<T> setter, Supplier<T> creator) {
        return Optional.<T> ofNullable(getter.get())
            .orElseGet(() -> {
                final T t = creator.get();
                if (setter != null) {
                    setter.accept(t);
                }
                return t;
            });
    }

    /** Add a new item to a Collection supplied by a getter. Call the setter with
     *  a new Collection given by the creator if getter returns null.
     *  Call setter if a new Collection was made.
     *
     *  @param item item to add to collection
     *  @param getter Supplier of a collection, which may return null
     *  @param setter called only if getter returns null and creator returns
     *              a new collection. May be null, in which case setting is ignored.
     *  @param creator called only when getter returns null.
     *  @return the original item
     */
    public static <T, C extends Collection<T>> T addToCollection(T item, Supplier<C> getter, Consumer<C> setter, Supplier<C> creator) {
        Collections.<C> getOrMake(getter, setter, creator).add(item);
        return item;
    }


    /** Add a new item to a set supplied by a getter. Call the setter with
     *  a new Hashset if getter returns null.
     *  @param item item to add to collection
     *  @param getter Supplier of a collection, which may return null
     *  @param setter called only if getter returns null and creator returns
     *              a new collection. May be null, in which case setting is ignored.
     *  @return the original item
     */
    public static <T> T addToSet(T item, Supplier<Set<T>> getter, Consumer<Set<T>> setter) {
        addToCollection(item, getter, setter, HashSet<T>::new);
        return item;
    }

    /** Find the first item in a collection that matches some filter predicate.
     *  Return empty if none found, or if collection is null or empty.
     *
     *  @param by predicate to filter collection items.
     *  @param coll May be null
     *  @return First found IdEntity item, or empty if none found.
     */
    public static <T> Optional<T> findFirstBy(Predicate<T> by, Collection<T> coll) {
        return coll == null ? Optional.<T> empty()
            : coll.stream()
                .filter(by)
                .findFirst();
    }

    /** Factory class is not to be instantiated */
    private Collections() {}
}
