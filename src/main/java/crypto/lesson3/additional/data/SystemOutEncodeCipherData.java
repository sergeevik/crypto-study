package crypto.lesson3.additional.data;

import crypto.lesson3.additional.HexUtils;

/**
 * Имплементация CipherData, для вывода шифрованных данных на консоль
 * + дополнение учебного проекта, аргументы для запуска на дешифровку
 */
public class SystemOutEncodeCipherData extends CipherData {
    public SystemOutEncodeCipherData(byte[] data, byte[] hash) {
        super(data, hash);
    }

    @Override
    public String toString() {
        String data = HexUtils.byteToString(this.data);
        String hash = HexUtils.byteToString(this.hash);
        return "hash [" + hash + "], encoded data = [" + data + "]\n" +
                "Аргументы для дешифровки: \n" +
                "-d " + data + " " + hash;
    }
}
