package com.wapitia.common;

import com.wapitia.common.Attr.GETTER;
import com.wapitia.common.Attr.SETTER;

import lombok.Value;

/**
 * Defines A getter/setter pair for a Continer containing the attribute.
 *
 * @param <A> Getter/Setter Attribute type
 * @param <C> Class or container type
 */
@Value(staticConstructor="of")
public class ConcreteAttr<A,C> implements CAttr<A> {

    private final C container;
    private final Attr<A,C> attr;

    /**
     * Gets the value from the container using the {@code getter} function
     * supplied in the constructor.
     * If the getter function is {@code null}, returns {@code null}.
     *
     * @return value A or {@code null} if getter is {@code null}.
     */
    @Override
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
    @Override
    public void set(A value) {
        attr.set(container, value);
    }

    /** Create a new ConcreteAttr instance
     * @param container concrete container
     * @param getter Attr getter function
     * @param setter Attr setter function
     * @return new ConcreteAttr instance
     */
    public static <A,C> ConcreteAttr<A,C> of(C container, GETTER<A,C> getter, SETTER<A,C> setter) {
        return ConcreteAttr.of(container, Attr.of(getter, setter));
    }
}
