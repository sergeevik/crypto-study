package crypto.lesson3.additional.service;

import crypto.lesson3.additional.data.CommandLineArgs;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * Сервис, который отвечает за создание Cipher инстанса по нужным правилам
 * Выбор алгоритма, заполнителей
 */
public class CipherService {

    private final KeyService keyHolder;

    public CipherService(KeyService keyHolder) {
        this.keyHolder = keyHolder;
    }

    public Cipher getCipher(CommandLineArgs args) throws Exception {
        SecretKey key = keyHolder.getKey();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init(args.mode, key, ivspec);
        return cipher;
    }
}
