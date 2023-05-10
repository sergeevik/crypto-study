package crypto.lesson7;

import java.util.Base64;

/**
 * Хранение шифрованных данных
 * <p>
 * Сохраняется в виде строки
 * отступ_ключа$соль$шифрованное_значение
 * <p>
 * По примеру bcrypt спринга
 */
public class EncryptedData {
    private final byte[] salt;
    private final int offset;
    private final byte[] encryptedText;

    public EncryptedData(byte[] salt, int offset, byte[] encryptedText) {
        this.salt = salt;
        this.offset = offset;
        this.encryptedText = encryptedText;
    }

    public static EncryptedData parse(String rawData) {
        String[] $s = rawData.split("\\$");
        int offset = Integer.parseInt($s[0]);
        byte[] salt = Base64.getDecoder().decode($s[1]);
        byte[] encodedText = Base64.getDecoder().decode($s[2]);
        return new EncryptedData(salt, offset, encodedText);
    }

    public String asString() {
        return offset + "$" + Base64.getEncoder().encodeToString(salt) + "$" + Base64.getEncoder().encodeToString(encryptedText);
    }

    public byte[] getSalt() {
        return salt;
    }

    public int getOffset() {
        return offset;
    }

    public byte[] getEncryptedText() {
        return encryptedText;
    }
}
