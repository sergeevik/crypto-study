package crypto.finalproject;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class KeyStoreWrapper {
    private final KeyStore keyStore;
    private final String keyAlias;
    private final char[] password;

    public KeyStoreWrapper(KeyStore keyStore, String keyAlias, String password) {
        this.keyStore = keyStore;
        this.keyAlias = keyAlias;
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Передан пустой пароль");
        }
        this.password = password.toCharArray();
    }

    public void load(Path pathToFile) throws Exception {
        if (pathToFile == null) {
            keyStore.load(null, null);
        } else {
            keyStore.load(new FileInputStream(pathToFile.toFile()), password);
        }
    }

    public void createNewKeyInStore() throws Exception {
        CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", "SHA1WithRSA");
        certAndKeyGen.generate(2048);
        X509Certificate cert = certAndKeyGen.getSelfCertificate(new X500Name("CN=ROOT"), (long) 365 * 24 * 3600);
        X509Certificate[] chain = new X509Certificate[1];
        chain[0] = cert;
        PrivateKey privateKey = certAndKeyGen.getPrivateKey();
        keyStore.setKeyEntry(keyAlias, privateKey, password, chain);
    }

    public PublicKey getPublicKey() throws Exception {
        X509Certificate cert = (X509Certificate) keyStore.getCertificate(keyAlias);
        return cert.getPublicKey();
    }

    public PrivateKey getPrivateKey() throws Exception {
        return (PrivateKey) keyStore.getKey(keyAlias, password);
    }

    public void save(Path pathToFile) throws Exception {
        if (pathToFile == null) {
            throw new RuntimeException("Передан null путь для сохранения keyStore");
        }
        keyStore.store(new FileOutputStream(pathToFile.toFile()), password);
    }
}
