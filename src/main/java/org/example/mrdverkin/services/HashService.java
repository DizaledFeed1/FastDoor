package org.example.mrdverkin.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashService {

    public static String hashSha256(String input) {
        StringBuilder hexString = new StringBuilder();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return hexString.toString();
    }
}
