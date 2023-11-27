package keboom.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

class AESUtilUnitTest {

    @Test
    void givenString_whenEncrypt_thenSuccess()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        // given
        String input = "baeldung";
        SecretKey key = AESUtil.generateKey(128);
        IvParameterSpec ivParameterSpec = AESUtil.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";

        // when
        String cipherText = AESUtil.encrypt(algorithm, input, key, ivParameterSpec);
        String plainText = AESUtil.decrypt(algorithm, cipherText, key, ivParameterSpec);

        // then
        Assertions.assertEquals(input, plainText);
    }





    @Test
    void givenPassword_whenEncrypt_thenSuccess()
            throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        // given
        String plainText = "www.baeldung.com";
        String password = "baeldung";
        String salt = "12345678";
        IvParameterSpec ivParameterSpec = AESUtil.generateIv();
        SecretKey key = AESUtil.getKeyFromPassword(password, salt);

        // when
        String cipherText = AESUtil.encryptPasswordBased(plainText, key, ivParameterSpec);
        System.out.println("cipherText: " + cipherText);

        String decryptedCipherText = AESUtil.decryptPasswordBased(cipherText, key, ivParameterSpec);
        System.out.println("decryptedCipherText: " + decryptedCipherText);

        // then
        Assertions.assertEquals(plainText, decryptedCipherText);
    }



    @Test
     void encrypt() {
        String value="baeldung";
        final String key = "aesEncryptionKey";
        final String initVector = "encryptionIntVec";
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            String s = Base64.encodeBase64String(encrypted);
            System.out.println(s);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void decrypt() {
        String encrypted = "f3abc1/iSw92hEfjeZeZxA==";
        final String key = "aesEncryptionKey";
        final String initVector = "encryptionIntVec";
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            System.out.println(new String(original));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void md5Test() {
        String passwd = "baeldung";
        String s = Md5Crypt.md5Crypt(passwd.getBytes());

        System.out.println(s);
        System.out.println(s.length());
    }
}