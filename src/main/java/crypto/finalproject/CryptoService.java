package crypto.finalproject;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class CryptoService {

    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String CIPHER_ALGORITHM = "RSA";

    public byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    public byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }


    public byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(data);
        return sign.sign();
    }

    public boolean verifySign(byte[] data, byte[] digitalSignature, PublicKey publicKey) throws Exception {
        Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);
        sign.initVerify(publicKey);
        sign.update(data);
        return sign.verify(digitalSignature);
    }
}