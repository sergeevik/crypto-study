package crypto.lesson3.additional.data;

import crypto.lesson3.additional.HexUtils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

/**
 * Разбираем и парсим аргументы командной строки
 */
public class CommandLineArgs {
    // допущение public, чтобы get/set не наращивать
    public final int mode;
    public final byte[] data;
    public final byte[] hash;

    private CommandLineArgs(int mode, byte[] data, byte[] hash) {
        this.mode = mode;
        this.data = data;
        this.hash = hash;
    }

    public static CommandLineArgs parse(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("Необходимо передать минимум 2 аргумента.");
        }
        int mode = parseMode(args[0]);
        checkArgsCountByMode(mode, args);
        return parseByMode(mode, args);
    }

    private static CommandLineArgs parseByMode(int mode, String[] args) {
        if (mode == Cipher.ENCRYPT_MODE) {
            return new CommandLineArgs(mode, args[1].getBytes(StandardCharsets.UTF_8), null);
        } else if (mode == Cipher.DECRYPT_MODE) {
            return new CommandLineArgs(mode, HexUtils.stringToByteArray(args[1]), HexUtils.stringToByteArray(args[2]));
        } else {
            throw new RuntimeException("Неизвестный режим работы");
        }
    }

    private static void checkArgsCountByMode(int mode, String[] args) {
        if (mode != Cipher.DECRYPT_MODE && mode != Cipher.ENCRYPT_MODE){
            throw new RuntimeException("Неизвестный режим работы");
        }
        if (mode == Cipher.ENCRYPT_MODE && args.length != 2) {
            throw new RuntimeException("Для шифрования надо передать вторым аргументом данные для шифрования");
        }
        if (mode == Cipher.DECRYPT_MODE && args.length != 3) {
            throw new RuntimeException("Для расшифровки надо передать вторым аргументом данные для шифрования" +
                    ", а третьим контрольную сумму");
        }
    }

    private static int parseMode(String modeFromArgs) {
        switch (modeFromArgs) {
            case "-e":
                return Cipher.ENCRYPT_MODE;
            case "-d":
                return Cipher.DECRYPT_MODE;
            default:
                throw new RuntimeException("Допистмые режимы работы -e (шифровать) -d (расшифровать)");
        }
    }
}
