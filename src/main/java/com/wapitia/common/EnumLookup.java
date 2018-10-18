package com.wapitia.common;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Provides a lookup by name of an enum string without
 *  having an exception thrown, as does Enum.valueOf()
 *  <p>
 *  Usage:
 *  <pre>
    import com.wapitia.common.EnumLookup;

    enum ScopeChoice {
        CodeOnly,
        HeaderOnly,
        All;

        public static EnumLookup&lt;ScopeChoice> lookup = new EnumLookup&lt;>(values());

        public static ScopeChoice byName(String scope, ScopeChoice alternate) {
            return lookup.byName(scope).orElse(alternate);
        }
    }
 *  </pre>
 *  @param <V> Enum type
 */
public class EnumLookup<V extends Enum<V>> {

    /** Holds both enum names and upper-case enum names, in case they are different. */
    private final Map<String,V> byName;

    /** Constructor takes a list of Enum values, and maps them by name. */
    public EnumLookup(V[] list) {
        Objects.requireNonNull(list);
        this.byName = new HashMap<String,V>(list.length);
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
        return Optional.ofNullable(byName
            .get(name.toUpperCase(Locale.ENGLISH)));
    }
}
