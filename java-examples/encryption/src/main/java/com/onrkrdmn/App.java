package com.onrkrdmn;

import com.onrkrdmn.crypto.PKCS5PasswordEncryptor;
import com.onrkrdmn.crypto.PasswordEncryptor;


public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Password encryption is running.....");

        String password = "test1234567";
        System.out.println("Plain Text Password: " + password);

        PasswordEncryptor encryptor = new PKCS5PasswordEncryptor();

        String encrypted = encryptor.encrypt(password);
        System.out.println("Encrypted password: " + encrypted);

        String decrypted = encryptor.decrypt(encrypted);
        System.out.println("Dencrypted password: " + decrypted);

        System.out.println("Password encryption finished......");

    }
}
