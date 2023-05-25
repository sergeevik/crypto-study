package crypto.finalproject.cli;

import crypto.finalproject.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;

public class DecryptCli {

    public static void main(String[] args) throws Exception {
        String fullPathToKeystore = args[0];
        String password = args[1];
        byte[] encryptedWord = HexUtils.stringToByteArray(args[2]);
        byte[] sign = HexUtils.stringToByteArray(args[3]);
        String keyAlias = args[4];

        Path pathToKeystore = Paths.get(fullPathToKeystore);
        KeyStoreType keyStoreType = KeyStoreType.fromFile(pathToKeystore);
        KeyStore keyStore = new KeyToolFactory().getByType(keyStoreType);
        KeyStoreWrapper keyStoreWrapper = new KeyStoreWrapper(keyStore, keyAlias, password);
        keyStoreWrapper.load(pathToKeystore);

        CryptoService cryptoService = new CryptoService();
        boolean isValidSign = cryptoService.verifySign(encryptedWord, sign, keyStoreWrapper.getPublicKey());
        byte[] decrypt = cryptoService.decrypt(encryptedWord, keyStoreWrapper.getPrivateKey());

        System.out.println("Расшифрованное значение: " + new String(decrypt));
        System.out.println("Подпись валидна?: " + (isValidSign ? "да" : "нет"));
    }
}