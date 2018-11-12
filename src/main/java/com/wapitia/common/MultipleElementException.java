package com.wapitia.common;


/**
 * Runtime exception thrown when multiple elements found where at most
 * one was expected.
 */
public class MultipleElementException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Messaged exception thrown when multiple elements found.
     * @param message informative message.
     */
    public MultipleElementException(String message) {
        super(message);
    }
}
