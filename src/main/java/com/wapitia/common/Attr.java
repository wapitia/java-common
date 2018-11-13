package com.wapitia.common;

import lombok.Value;

/**
 * Wraps the getter and setter functions for an attribute in a container.
 *
 * @param <A> Getter/Setter Attribute type
 * @param <C> Class or container type
 */
@Value(staticConstructor="of")
public class Attr<A,C> {

    /**
     * Getter functional interface
     * @param <A> value type
     * @param <C> container type
     */
    @FunctionalInterface
    public static interface GETTER<A,C> {

        /**
         * Get value {@code A} from container {@code C}.
         * @param container the container with the attribute.
         * @return value {@code A}.
         */
        A get(C container);
    }

    /**
     * Setter functional interface
     * @param <A> value type
     * @param <C> container type
     */
    @FunctionalInterface
    public static interface SETTER<A,C> {

        /**
         * Set value {@code A} in container {@code C}.
         * @param value value to set
         * @param container the container with the attribute.
         */
        void set(C container, A value);
    }

    private final GETTER<A,C> getter;

    private final SETTER<A,C> setter;

    /**
     * Gets the value from the container using the {@code getter} function
     * supplied in the constructor.
     * If the getter function is {@code null}, returns {@code null}.
     *
     * @param container the container with the attribute.
     * @return value A or {@code null} if getter is {@code null}.
     */
    public A get(C container) {
        return getter != null ? getter.get(container) : (A) null;
    }

    /**
     * Sets the value in the container using the {@code setter} function
     * supplied in the constructor.
     * If the setter function is {@code null} does nothing.
     *
     * @param value value to set
     * @param container the container with the attribute.
     */
    public void set(C container, A value) {
        if (setter != null) setter.set(container, value);
    }
}
