package crypto.lesson5;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

public class Encrypt {
    private static final String STRING_FOR_ENCODE = "Java";

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateKeyPair();
        byte[] encryptedText = encrypt(STRING_FOR_ENCODE.getBytes(StandardCharsets.UTF_8), keyPair);
        KeyPair keyPairForSignature = generateKeyPair();
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(keyPairForSignature.getPrivate());
        signature.update(encryptedText);
        byte[] sign = signature.sign();

//        Проверка, что оно работает
        signature.initVerify(keyPairForSignature.getPublic());
        signature.update(encryptedText);
        boolean isSignOk = signature.verify(sign);
        System.out.println("Sign: " + (isSignOk ? "ok" : "error"));

        byte[] decrypted = decrypt(encryptedText, keyPair);
        System.out.println(new String(decrypted));
    }

    private static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] encrypt(byte[] text, KeyPair keyPair) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        return cipher.doFinal(text);
    }

    private static byte[] decrypt(byte[] text, KeyPair keyPair) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        return cipher.doFinal(text);
    }
}
