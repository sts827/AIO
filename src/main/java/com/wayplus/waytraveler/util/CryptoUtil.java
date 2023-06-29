package com.wayplus.waytraveler.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CryptoUtil {
    private String encryptKey;
    private String salt;
    private String iv;

    public String getEncryptKey() {
        return encryptKey;
    }

    public String getSalt() {
        return salt;
    }

    public String getIv() {
        return iv;
    }

    public String generateRandomEncryptKey(String encryptKey){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encryptKey != null && encryptKey.length() > 32){
            this.encryptKey = encryptKey.substring(0,32);
        }else {
            if(encryptKey != null) this.encryptKey = encoder.encode(encryptKey).substring(0, 32);
            else this.encryptKey = encoder.encode("RandomEncryptKey").substring(0, 32);
        }
        return this.encryptKey;
    }

    public String generateRandomSalt(String saltKey){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(saltKey != null && saltKey.length() > 32){
            this.salt = saltKey.substring(0,32);
        }else {
            if(saltKey != null) this.salt = encoder.encode(saltKey).substring(10, 42);
            else this.salt = encoder.encode("RandomSaltKey").substring(10, 42);
        }
        return this.salt;
    }

    public String generateRandomIv(String ivKey){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(ivKey != null && ivKey.length() > 16){
            this.iv = ivKey.substring(8,24);
        }else {
            if(ivKey != null) this.iv = encoder.encode(ivKey).substring(8, 24);
            else this.iv = encoder.encode("RandomIvKey").substring(8, 24);
        }
        return this.iv;
    }

    public void generateRandomSeed(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String key = encoder.encode("RandomEncryptKey");
        this.encryptKey = key.substring(0, 32);
        this.salt = key.substring(10, 42);
        this.iv = key.substring(8, 24);
    }

    public static String aesEncode(String keyString, String encryptKey, String ivKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        byte[] keyData = encryptKey.getBytes();
        SecretKey secretKey = new SecretKeySpec(keyData, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(ivKey.getBytes()));
        byte[] encrypted = cipher.doFinal(keyString.getBytes(StandardCharsets.UTF_8));
        String encodeString = new String(Base64.encodeBase64(encrypted));
        return encodeString;
    }

    public static String aesDecode(String keyString, String encryptKey, String ivKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        byte[] keyData = encryptKey.getBytes();
        SecretKey secretKey = new SecretKeySpec(keyData, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivKey.getBytes(StandardCharsets.UTF_8)));

        byte[] decrypted = Base64.decodeBase64(keyString.getBytes());
        String decodeString = new String(cipher.doFinal(decrypted), StandardCharsets.UTF_8);
        return decodeString;
    }

}
