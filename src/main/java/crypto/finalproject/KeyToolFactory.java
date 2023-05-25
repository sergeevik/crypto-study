package crypto.finalproject;

import java.security.KeyStore;

public class KeyToolFactory {
    public KeyStore getByType(KeyStoreType type) throws Exception {
        return KeyStore.getInstance(type.name());
    }
}