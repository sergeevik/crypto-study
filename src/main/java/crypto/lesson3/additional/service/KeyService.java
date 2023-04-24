package crypto.lesson3.additional.service;

import crypto.lesson3.additional.Lesson3Additional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;

/**
 * Сервис для получения ключа, в данном случае из файла
 */
public class KeyService {

    public SecretKey getKey() throws Exception {
        InputStream keyStream = Lesson3Additional.class.getClassLoader().getResourceAsStream("lesson3/key.AES");
        if (keyStream == null || keyStream.available() < 1) {
            throw new RuntimeException("Файл ключа не найден");
        }
        byte[] rawData = keyStream.readAllBytes();
        return new SecretKeySpec(rawData, 0, rawData.length, "AES");
    }
}
