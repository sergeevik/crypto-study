package crypto.lesson7;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;

// Вариант с сохранением шифрованных данных в строку, по примеру bcrypt
// Запуск с параметром пароля
// Плюсом: выбор для одной строки случайно соли и случайного отступа
public class EncryptWithSaveAsString {
    private static final String STRING_FOR_ENCODE = "Java";
    public static final String SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA256";
    public static final String SECRET_KEY_ALGORITHM = "AES";
    public static final int SECRET_KEY_LENGTH = 32;
    public static final String CIPHER_ALGORITHM = "AES";
    public static final String SUN_EC_PROVIDER = "SunEC";

    public static void main(String[] args) throws Exception {
        char[] password = args[0].toCharArray();
        String encrypt = encrypt(STRING_FOR_ENCODE.getBytes(StandardCharsets.UTF_8), password)
                .asString();

        System.out.println(encrypt);

        KeyPair keyPairForSignature = generateKeyPairEc();
        Signature signature = Signature.getInstance("SHA256withECDSA", SUN_EC_PROVIDER);
        signature.initSign(keyPairForSignature.getPrivate());
        signature.update(encrypt.getBytes(StandardCharsets.UTF_8));
        byte[] sign = signature.sign();

//        Проверка, что оно работает
        signature.initVerify(keyPairForSignature.getPublic());
        signature.update(encrypt.getBytes(StandardCharsets.UTF_8));
        boolean isSignOk = signature.verify(sign);
        System.out.println("Sign: " + (isSignOk ? "ok" : "error"));

        byte[] decrypted = decrypt(EncryptedData.parse(encrypt), password);
        System.out.println(new String(decrypted));
    }

    private static KeyPair generateKeyPairEc() throws Exception {
        ECGenParameterSpec spec = new ECGenParameterSpec("secp224r1");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", SUN_EC_PROVIDER);
        keyPairGenerator.initialize(spec);
        return keyPairGenerator.generateKeyPair();
    }

    private static EncryptedData encrypt(byte[] text, char[] password) throws Exception {
        SecureRandom secureRandom = SecureRandomHolder.getSecureRandom();
        byte[] salt = secureRandom.generateSeed(64);
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, Short.MAX_VALUE, 2048);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
        byte[] encodedKey = keyFactory.generateSecret(keySpec).getEncoded();
        // Создаем случайный отступ для выбора куска ключа, т.к. AES принимает максимум 32 значный ключ
        int offset = secureRandom.nextInt(encodedKey.length - SECRET_KEY_LENGTH);
        SecretKey key = new SecretKeySpec(encodedKey, offset, SECRET_KEY_LENGTH, SECRET_KEY_ALGORITHM);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedText = cipher.doFinal(text);
        return new EncryptedData(salt, offset, encryptedText);
    }

    private static byte[] decrypt(EncryptedData encryptedData, char[] password) throws Exception {
        PBEKeySpec keySpec = new PBEKeySpec(password, encryptedData.getSalt(), Short.MAX_VALUE, 2048);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
        byte[] encodedKey = keyFactory.generateSecret(keySpec).getEncoded();
        SecretKey key = new SecretKeySpec(encodedKey, encryptedData.getOffset(), SECRET_KEY_LENGTH, SECRET_KEY_ALGORITHM);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData.getEncryptedText());
    }
}
