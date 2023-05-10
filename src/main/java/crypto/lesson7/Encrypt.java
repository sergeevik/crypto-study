package crypto.lesson7;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;

//Решение в лоб
public class Encrypt {
    private static final String STRING_FOR_ENCODE = "Java";

    public static void main(String[] args) throws Exception {
        char[] password = args[0].toCharArray();
        byte[] salt = SecureRandomHolder.getSecureRandom().generateSeed(64);
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, Short.MAX_VALUE, 2048);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        // keySpec возвращает ключ длинной 256, AES поддерживает только до 32, взял от 0 до 32
        SecretKey key = new SecretKeySpec(keyFactory.generateSecret(keySpec).getEncoded(), 0, 32, "AES");
        byte[] encryptedText = encrypt(STRING_FOR_ENCODE.getBytes(StandardCharsets.UTF_8), key);
        KeyPair keyPairForSignature = generateKeyPairEc();
        Signature signature = Signature.getInstance("SHA256withECDSA", "SunEC");
        signature.initSign(keyPairForSignature.getPrivate());
        signature.update(encryptedText);
        byte[] sign = signature.sign();

//        Проверка, что оно работает
        signature.initVerify(keyPairForSignature.getPublic());
        signature.update(encryptedText);
        boolean isSignOk = signature.verify(sign);
        System.out.println("Sign: " + (isSignOk ? "ok" : "error"));

        byte[] decrypted = decrypt(encryptedText, key);
        System.out.println(new String(decrypted));
    }

    private static KeyPair generateKeyPairEc() throws Exception {
        ECGenParameterSpec spec = new ECGenParameterSpec("secp224r1");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "SunEC");
        keyPairGenerator.initialize(spec);
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] encrypt(byte[] text, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(text);
    }

    private static byte[] decrypt(byte[] text, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(text);
    }
}
