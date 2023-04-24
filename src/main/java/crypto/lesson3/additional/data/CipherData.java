package crypto.lesson3.additional.data;

import java.util.function.Consumer;

/**
 * Дто для данных шифрования, поля public как допущение учебного проекта
 */
public abstract class CipherData {
    final byte[] data;
    final byte[] hash;

    public CipherData(byte[] data, byte[] hash) {
        this.data = data;
        this.hash = hash;
    }

    public void then(Consumer<CipherData> action) {
        action.accept(this);
    }
}
