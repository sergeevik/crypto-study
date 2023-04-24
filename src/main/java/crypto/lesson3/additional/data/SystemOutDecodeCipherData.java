package crypto.lesson3.additional.data;


import java.nio.charset.StandardCharsets;

/**
 * Имплементация CipherData, для вывода дешифрованных данных на консоль
 */
public class SystemOutDecodeCipherData extends CipherData {
    public SystemOutDecodeCipherData(byte[] data, byte[] hash) {
        super(data, hash);
    }

    @Override
    public String toString() {
        return "decoded data = [" + new String(data, StandardCharsets.UTF_8) + "]";
    }
}
