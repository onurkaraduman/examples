package com.onrkrdmn.crypto;

public interface PasswordEncryptor {

	String encrypt(String pass) throws Exception;

	String decrypt(String pass) throws Exception;
}
