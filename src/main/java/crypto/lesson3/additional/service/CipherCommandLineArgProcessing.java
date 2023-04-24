package crypto.lesson3.additional.service;

import crypto.lesson3.additional.data.CipherData;
import crypto.lesson3.additional.data.CommandLineArgs;
import crypto.lesson3.additional.data.SystemOutDecodeCipherData;
import crypto.lesson3.additional.data.SystemOutEncodeCipherData;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CipherCommandLineArgProcessing {

    private final HashService hashService;
    private final Cipher cipher;

    public CipherCommandLineArgProcessing(HashService hashService, Cipher cipher) {
        this.hashService = hashService;
        this.cipher = cipher;
    }

    public CipherData process(CommandLineArgs args) throws Exception {
        switch (args.mode) {
            case Cipher.DECRYPT_MODE: return decryptWithCheckHash(args);
            case Cipher.ENCRYPT_MODE: return encrypt(args);
            default: throw new RuntimeException();
        }
    }

    private SystemOutDecodeCipherData decryptWithCheckHash(CommandLineArgs args) throws Exception {
        byte[] decrypt = cipher.doFinal(args.data);
        String decryptString = new String(decrypt, StandardCharsets.UTF_8);
        byte[] actualHash = hashService.createHash(decryptString);
        if (!Arrays.equals(args.hash, actualHash)) {
            throw new RuntimeException("Контрольные суммы не совпадают");
        }
        return new SystemOutDecodeCipherData(decrypt, actualHash);
    }

    private SystemOutEncodeCipherData encrypt(CommandLineArgs args) throws Exception {
        byte[] hash = hashService.createHash(args.data);
        byte[] encrypt = cipher.doFinal(args.data);
        return new SystemOutEncodeCipherData(encrypt, hash);
    }

}
