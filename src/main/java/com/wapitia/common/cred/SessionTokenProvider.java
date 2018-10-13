package com.wapitia.common.cred;

/**
 * @param <T> the application-specific Session token
 */
@FunctionalInterface
public interface SessionTokenProvider<T extends SessionToken> {

    /**
     * application-specific implementation returns its custom session
     * token for the given username:password and application-supplied
     * module
     *
     * @param isValid true only when an authorization string is supplied with
     *        a parsable username:password.
     * @param username supplied by an authentication request
     * @param password clear text password.
     * @param module application-specific module or sub-section.
     * @return application-specific session token
     */
    T lookupToken(boolean isValid, String username, String password, String module);

}
