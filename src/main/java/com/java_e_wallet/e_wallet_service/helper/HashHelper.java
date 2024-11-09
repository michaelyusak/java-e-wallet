package com.java_e_wallet.e_wallet_service.helper;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashHelper {

    public static String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    public static boolean compare(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }
}
