package com.onrkrdmn.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PKCS5PasswordEncryptor implements PasswordEncryptor {

    // This is added end of the password just before hashing
    private final static byte[] SALT = "$C&F)J@N".getBytes();

    // This is used for encryption and decryption key - 128bit
    private final static String ENCRYPT_KEY = "%C*F-JaNdRgUjXn2";

    private final static int ITERATION = 40000;
    private final static int KEY_LENGTH = 128;

    private static final String ENCRYPTION_PREFIX = "ENC~";

    @Override
    public String encrypt(String pass) throws Exception {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = createSecretKey(ENCRYPT_KEY.toCharArray(), SALT, ITERATION, KEY_LENGTH);
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(pass.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return ENCRYPTION_PREFIX + base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    @Override
    public String decrypt(String pass) throws Exception {
        if (hasPassEncryptedPrefix(pass)) {
            pass = pass.substring(ENCRYPTION_PREFIX.length());
        }
        String iv = pass.split(":")[0];
        String property = pass.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = createSecretKey(ENCRYPT_KEY.toCharArray(), SALT, ITERATION, KEY_LENGTH);
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    public static boolean hasPassEncryptedPrefix(String property) {
        if (property != null) {
            return property.startsWith(ENCRYPTION_PREFIX);
        }
        return false;
    }

    private String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] base64Decode(String property) {
        return Base64.getDecoder().decode(property);
    }
}
