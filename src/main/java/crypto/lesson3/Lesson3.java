package crypto.lesson3;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class Lesson3 {
    public static void main(String[] args) throws Exception {
        byte[] secretWord = args[0].getBytes(StandardCharsets.UTF_8);
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecretKey secretKey = keyGenerator.generateKey();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] hash = md5.digest(secretWord);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(secretWord);
        String humaReadEncryptedWord = Base64.getEncoder().encodeToString(encrypted);
        String humaReadEncryptedHash = Base64.getEncoder().encodeToString(hash);
        System.out.println("hash = " + humaReadEncryptedHash);
        System.out.println(humaReadEncryptedWord);
    }
}
