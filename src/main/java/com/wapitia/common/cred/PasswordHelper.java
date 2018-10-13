package com.wapitia.common.cred;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHelper {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError(
                "Error while hashing a password: " + e.getMessage(), e);
        }
        finally {
            spec.clearPassword();
        }
    }

    public static String encrypt(String password, byte[] salt) {
        final byte[] encrypted = hash(password.toCharArray(), salt);
        final String result = Base64.getEncoder().encodeToString(encrypted);
        return result;
    }

}