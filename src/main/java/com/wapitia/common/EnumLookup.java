package com.wapitia.common;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Provides a lookup by name of an enum string without
 *  having an exception thrown, as does Enum.valueOf()
 *  <p>
 *  The typical usage is to embed a static instance of EnumLookup in the
 *  enum itself, then having a static method also in the enum to perform
 *  the lookup, as shown.
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
 *
 *  @param <E> Enum type, extends {@code Enum<E>}
 */
public class EnumLookup<E extends Enum<E>> {

    /** Holds both enum names and upper-case enum names, in case they are different. */
    private final Map<String,E> byName;

    /** Constructor takes a list of Enum values, and maps them by name.
     *  This typically is provided by the enum's {@code values()}
     *  static function.
     *
     *  @param list A list of enum elements from the enum's values() function.
     */
    public EnumLookup(E[] list) {
        Objects.requireNonNull(list);
        this.byName = new HashMap<String,E>(list.length);
        for (E item: list) {
            byName.put(item.name(), item);
            byName.put(item.name().toUpperCase(Locale.ENGLISH), item);
        }
    }

    /** Return Some Enum value corresponding to the name, or None if it doesn't exist.
     *  The case match of the name must be exact.
     *
     * @param name enum name to look for
     * @return Enum value if found.
     */
    public Optional<E> byName(String name) {
        return Optional.ofNullable(byName.get(name));
    }

    /** Return Some Enum value corresponding to the name, or None if it doesn't exist.
     *  The lookup is case-insensitive.
     *
     * @param name case insensitive enum name to look for
     * @return Enum value if found
     */
    public Optional<E> byNameCaseInsensitive(String name) {
        return Optional.ofNullable(byName
            .get(name.toUpperCase(Locale.ENGLISH)));
    }
}
