package com.wapitia.common;

import lombok.Value;

/**
 * Defines A getter/setter pair for a Continer containing the attribute.
 *
 * @param <A> Getter/Setter Attribute type
 * @param <C> Class or container type
 */
@Value(staticConstructor="of")
public class ConcreteAttr<A,C> {

    private final Attr<A,C> attr;
    private final C container;

    /**
     * Gets the value from the container using the {@code getter} function
     * supplied in the constructor.
     * If the getter function is {@code null}, returns {@code null}.
     *
     * @return value A or {@code null} if getter is {@code null}.
     */
    public A get() {
        return attr.get(container);
    }

    /**
     * Sets the value in the container using the {@code setter} function
     * supplied in the constructor.
     * If the setter function is {@code null} does nothing.
     *
     * @param value value to set
     */
    public void set(A value) {
        attr.set(container, value);
    }
}
