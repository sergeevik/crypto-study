package crypto.finalproject.cli;

import crypto.finalproject.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Random;

public class EncryptCli {

    public static final String PRIVATE_KEY_ALIAS = "private_key";

    public static void main(String[] args) throws Exception {
        String wordToEncrypt = args[2];
        String password = args[1];
        String randomMethod = args[0];

        KeyStoreType keyStoreType = getKeyToolTypeByRandom(randomMethod);
        KeyStore keyStore = new KeyToolFactory().getByType(keyStoreType);
        KeyStoreWrapper keyStoreWrapper = new KeyStoreWrapper(keyStore, PRIVATE_KEY_ALIAS, password);
        keyStoreWrapper.load(null);
        keyStoreWrapper.createNewKeyInStore();

        Path workDir = getWorkDir();
        Path pathToKeytool = workDir.resolve("keystore." + keyStoreType.getExtension());
        keyStoreWrapper.save(pathToKeytool);

        CryptoService cryptoService = new CryptoService();
        byte[] encrypt = cryptoService.encrypt(wordToEncrypt.getBytes(StandardCharsets.UTF_8), keyStoreWrapper.getPublicKey());
        byte[] sign = cryptoService.sign(encrypt, keyStoreWrapper.getPrivateKey());

        System.out.println("Путь до jks: " + pathToKeytool);
        System.out.println("Пароль стора: " + password);
        System.out.println("Зашифрованное значение: " + HexUtils.byteToString(encrypt));
        System.out.println("Имя ключа: " + PRIVATE_KEY_ALIAS);
        System.out.println("Подпись: " + HexUtils.byteToString(sign));

        System.out.println("\nАргументы для передачи в приложение расшифровки: \n");
        System.out.println(pathToKeytool + " " + password + " " + HexUtils.byteToString(encrypt) + " " + HexUtils.byteToString(sign) + " " + PRIVATE_KEY_ALIAS);

    }

    private static Path getWorkDir() throws IOException {
        Path workDir = Paths.get(System.getProperty("user.home")).resolve("crypto_final");
        if (Files.notExists(workDir)) {
            Files.createDirectories(workDir);
        }
        return workDir;
    }

    private static KeyStoreType getKeyToolTypeByRandom(String randomMethod) {
        RandomType randomType = RandomType.parse(randomMethod);
        Random random = new RandomFactory().getRandom(randomType);
        KeyStoreType keyToolType = KeyStoreType.random(random);
        return keyToolType;
    }
}