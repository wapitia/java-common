package com.wapitia.common;

/**
 * Get/Set interface for an item {@code A}.
 *
 * @param <A> value type
 */
public interface CAttr<A> {

    /**
     * Gets the value from its container.
     * @return value A or {@code null} if getter is {@code null}.
     */
    A get();

    /**
     * Sets the value in its container.
     * If the setter function is {@code null} does nothing.
     *
     * @param value value to set
     */
    void set(A value);

}
