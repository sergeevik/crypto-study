package crypto.lesson9;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class Encrypt {
    public static void main(String[] args) throws Exception {
        String pathToJks = args[0]; // C:\Users\Terry\crypto_lesson_9\keystore.jks
        int rsaKeyLength = Integer.parseInt(args[1]); // 2048

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, null);

        CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", "SHA1WithRSA");
        certAndKeyGen.generate(rsaKeyLength);
        X509Certificate cert = certAndKeyGen.getSelfCertificate(new X500Name("CN=ROOT"), (long) 365 * 24 * 3600);

        X509Certificate[] chain = new X509Certificate[1];
        chain[0] = cert;

        PrivateKey privateKey = certAndKeyGen.getPrivateKey();
        keyStore.setKeyEntry(Constants.KEY_ALIAS, privateKey, Constants.KEY_PASSWORD.toCharArray(), chain);
        keyStore.store(new FileOutputStream(pathToJks), Constants.KEYSTORE_PASSWORD.toCharArray());

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, certAndKeyGen.getPublicKey());
        byte[] bytes = cipher.doFinal("java".getBytes(StandardCharsets.UTF_8));
        String encodedValueBase64 = Base64.getEncoder().encodeToString(bytes);

        System.out.println("Путь до jks: " + pathToJks);
        System.out.println("Зашифрованное значение: " + encodedValueBase64);
        System.out.println("Имя ключа: " + Constants.KEY_ALIAS);
    }

}
