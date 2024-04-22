package com.javasecurity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helpers {
    public static String encryptPassword(String pureTextPassword) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(pureTextPassword.getBytes());
            BigInteger bigInteger = new BigInteger(1, messageDigest.digest());
            return bigInteger.toString(16);
        } catch (NoSuchAlgorithmException exception) {
            throw new Error(exception);
        }
    }
}
