package com.wapitia.common;

/** String helper functions. */
public class Strings {

    /** String of length 0 */
    public static final String Empty = "";

    /** Return the parameter s unless s in null, in which case return an empty string.
     *  @param s the incoming string, may be null.
     *  @return s or blank, but never null.
     */
    public static String safe(String s) {
        return s == null ? Empty : s;
    }

    /** Return the leftmost {@code size} characters of String {@code s},
     *  unless s is equal to or shorter than {@code size} or is null,
     *  in which case s is returned unchanged.
     *
     *  @param size maximum size of string to return
     *  @param s string to check
     *  @return the left-most size characters of s, or null if s is null.
     */
    public static String leftmost(int size, String s) {
        if (s == null || s.length() <= size)
            return s;
        else
            return s.substring(0, size);
    }

    /** Return the rightmost {@code size} characters of String {@code s},
     *  unless s is equal to or shorter than {@code size} or is null,
     *  in which case s is returned unchanged.
     *
     *  @param size maximum size of string to return
     *  @param s string to check
     *  @return the right-most size characters of s, or null if s is null.
     */
    public static String rightmost(int size, String s) {
        if (s == null || s.length() <= size)
            return s;
        else
            return s.substring(s.length() - size);
    }

    /** Constructor is private as this is a factory class. */
    private Strings() {}

}
