package crypto.lesson9;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;

public class Decrypt {
    public static void main(String[] args) throws Exception {
        String pathToJks = args[0];
        String keyAlias = args[1];
        String encryptedTextBase64 = args[2];

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(pathToJks), Constants.KEYSTORE_PASSWORD.toCharArray());

        Cipher cipher = Cipher.getInstance("RSA");
        Key key = keyStore.getKey(keyAlias, Constants.KEY_PASSWORD.toCharArray());
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encodedBytes = Base64.getDecoder().decode(encryptedTextBase64);
        byte[] decryptedBytes = cipher.doFinal(encodedBytes);
        System.out.println("Расшифрованное значение: " + new String(decryptedBytes));
    }

}
