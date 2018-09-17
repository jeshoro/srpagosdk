package sr.pago.sdk.connection;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sr.pago.sdk.definitions.Definitions;

/**
 * Created by Rodolfo on 09/08/2015.
 */
public class Encryptor {

    private Encryptor(){

    }

    public static String rsaEncrypt(final String plain) throws Exception {
        byte[] encryptedBytes;
        try {
            Cipher cipher = Cipher.getInstance(Definitions.CIPHER());
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
            encryptedBytes = cipher.doFinal(plain.getBytes());
        } catch (Exception e) {
            throw e;
        }

        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    /**
     * This method encrypt the payment json object
     */
    public static String aesEncrypt(String raw, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), Definitions.AES());
        Cipher cipher = Cipher.getInstance(Definitions.AES());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return Base64.encodeToString(cipher.doFinal(raw.getBytes()), Base64.DEFAULT);
    }

    public static String getRandomKey() {
        return new BigInteger(160, new SecureRandom()).toString(32);
    }

    private static PublicKey getPublicKey() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
        String key = Definitions.AES_KEY();
        byte[] byteKey = Base64.decode(key.getBytes(Definitions.UTF()), Base64.DEFAULT);

        X509EncodedKeySpec x509PublicKey = new X509EncodedKeySpec(byteKey);
        KeyFactory kf = KeyFactory.getInstance(Definitions.RSA());

        return kf.generatePublic(x509PublicKey);
    }
}
