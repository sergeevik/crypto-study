package crypto.lesson3.additional.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Сервис генерации хеша
 */
public class HashService {

    public static final String ALGORITHM = "MD5";

    public byte[] createHash(String data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(ALGORITHM);
        return md5.digest(data.getBytes(StandardCharsets.UTF_8));
    }

    public byte[] createHash(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(ALGORITHM);
        return md5.digest(data);
    }
}
