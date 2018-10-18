package com.wapitia.common;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Provides a lookup by name of an enum string without
 *  having an exception thrown, as does Enum.valueOf()
 *
 *  @param <V> Enum type
 */
public class EnumLookup<V extends Enum<V>> {

    private final Map<String,V> byName;
    private final Map<String,V> byNameCaseInsensitive;

    /** Constructor takes a list of Enum values, and maps them by name. */
    public EnumLookup(V[] list) {
        Objects.requireNonNull(list);
        this.byName = new HashMap<String,V>(list.length);
        this.byNameCaseInsensitive = new HashMap<String,V>(list.length);
        for (V item: list) {
            byName.put(item.name(), item);
            byName.put(item.name().toUpperCase(Locale.ENGLISH), item);
        }
    }

    /** Return Some Enum value corresponding to the name, or None if it doesn't exist.
     *  The case match of the name must be exact.
     */
    public Optional<V> byName(String name) {
        return Optional.ofNullable(byName.get(name));
    }

    /** Return Some Enum value corresponding to the name, or None if it doesn't exist.
     *  The lookup is case-insensitive.
     */
    public Optional<V> byNameCaseInsensitive(String name) {
        return Optional.ofNullable(byNameCaseInsensitive
            .get(name.toUpperCase(Locale.ENGLISH)));
    }
}
