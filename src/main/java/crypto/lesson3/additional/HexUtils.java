package crypto.lesson3.additional;

import java.util.Base64;

/**
 * Утилиты для преобразования массива байт в строку, для ввода/вывода
 */
public class HexUtils {

    public static String byteToString(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] stringToByteArray(String data) {
        return Base64.getDecoder().decode(data);
    }
}
