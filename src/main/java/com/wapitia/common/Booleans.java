package com.wapitia.common;

/** Boolean helper functions. */
public class Booleans {

    /** Returns {@code true} if the {@code Boolean} argument is not {@code null}
     *  and is {@true}.
     *
     *  @param b Boolean to test, may be {@code null}.
     *  @return true iff {@code b} is non-{@code null} and {@code true}.
     */
    public static boolean isTrue(Boolean b) {
        return b != null && b.booleanValue();
    }

    /** Returns {@code true} if the {@code Boolean} argument is {@code null}
     *  or {@false}.
     *
     *  @param b Boolean model, may be {@code null}.
     *  @return true iff {@code b} is {@code null} or {@code false}.
     */
    public static boolean isNullOrFalse(Boolean b) {
        return ! isTrue(b);
    }

}
