package com.wapitia.common.cred;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class HttpBasicCredentials {

    private final byte[] passwordSalt;

    public HttpBasicCredentials(String passwordSalt) {
        this.passwordSalt = passwordSalt.getBytes();
    }

    // tuple(isvalid, username, password), (Boolean, String, String)
    private static class ParsedAuth {

        final boolean isValid;
        final String username;
        final String password;

        ParsedAuth(boolean isValid, String username, String password) {
            this.isValid = isValid;
            this.username = username;
            this.password = password;
        }

        // Authorization String should be of the form "Basic RTuoBase64Xyz=="
        static ParsedAuth parse(String authString) {

            final String[] authParts = authString.split("\\s+");
            if (authParts.length != 2 || !authParts[0].equals("Basic")) {
                return new ParsedAuth(false, "", "");
            }
            try {
                // Decode the data base64 part back to username:password
                final byte[] bytes = Base64.getDecoder().decode(authParts[1]);
                // parse the string into username:password pair
                // assume first colon is the separator, and _not_ part of
                // username
                final String decodedAuth = new String(bytes);
                final int whereColon = decodedAuth.indexOf(':');
                final String username;
                final String password;
                if (whereColon == -1) {
                    username = decodedAuth;
                    password = "";
                }
                else {
                    username = decodedAuth.substring(0, whereColon);
                    password = decodedAuth.substring(whereColon + 1);
                }
                return new ParsedAuth(true, username, password);
            }
            catch (IllegalArgumentException e) {
                // thrown by decode() when string isn't valid Base64,
                return new ParsedAuth(false, "", "");
            }

        }

    }

    /**
     * Server side basic authentication.
     *
     * @param authString
     *            authentication string of the form "Basic RTuoBase64Xyz". Must
     *            not be null.
     * @param module
     *            application-supplied submodule differentiates separate rest
     *            sections. Must not be null.
     * @param tokenGen
     *            application-specific SessionToken generator that provides the
     *            application-specific SesssionToken given username, password,
     *            module,
     *
     * @return application SessionToken derivative
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public <T extends SessionToken> T getSessionToken(String authString,
        String module, SessionTokenProvider<T> tokenGen) throws UnsupportedEncodingException, GeneralSecurityException {

        final ParsedAuth pa = ParsedAuth.parse(authString);
        final String encryptedPassword = PasswordHelper.encrypt(pa.password, passwordSalt);
        T result = tokenGen.lookupToken(pa.isValid, pa.username, encryptedPassword, module);
        return result;
    }
}
