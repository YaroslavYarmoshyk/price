package com.prices.service.impl;

import com.prices.exception.SystemException;
import com.prices.service.EncryptingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import static com.prices.util.Constants.ENCRYPTION_KEY;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

@Service
@Slf4j
public class EncryptingServiceImpl implements EncryptingService {
    private static final String UTF8 = "UTF-8";
    private static final String AES = "AES";
    private static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    private static final String SUN_JSE = "SunJCE";
    private static final int IV_SIZE = 16;

    @Override
    public String encryptJwt(String string) {
        final String encryptionKey = ENCRYPTION_KEY;
        final byte[] encrypted = encrypt(string, encryptionKey);
        final String encryptedString = Base64.getEncoder().encodeToString(encrypted);

        return UriUtils.encodeQueryParam(encryptedString, UTF8);
    }

    @Override
    public String decryptJwt(String encodedString) {
        final String encryptionKey = ENCRYPTION_KEY;
        final String nonUrlSafeString = UriUtils.decode(encodedString, UTF8);
        final byte[] decodedBase64 = Base64.getDecoder().decode(nonUrlSafeString);

        return decrypt(decodedBase64, encryptionKey);
    }

    private byte[] encrypt(String jwt, String encryptionKey) {

        try {
            final Cipher cipher;
            final byte[] iv = generateIv();
            cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING, SUN_JSE);

            final SecretKeySpec key = new SecretKeySpec(getBytes(encryptionKey), AES);
            cipher.init(ENCRYPT_MODE, key, new IvParameterSpec(iv));

            final byte[] encrypted = cipher.doFinal(getBytes(jwt));

            return joinByteArrays(encrypted, iv);
        } catch (final GeneralSecurityException e) {
            log.warn("Unable to encrypt jwt");
            throw new SystemException("Unable to encrypt jwt");
        }
    }

    private String decrypt(byte[] cipherText, String encryptionKey) {
        try {
            final Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING, SUN_JSE);
            final SecretKeySpec key = new SecretKeySpec(getBytes(encryptionKey), AES);
            cipher.init(DECRYPT_MODE, key, new IvParameterSpec(getIvFromJoinByteArray(cipherText)));

            return new String(cipher.doFinal(getCipherFromJoinByteArray(cipherText)), StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            log.warn("Unable to decrypt jwt");
            throw new SystemException("Unable to decrypt jwt");
        }
    }

    private byte[] generateIv() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);

        return iv;
    }

    private static byte[] getBytes(final String encryptKey) {
        return encryptKey.getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] joinByteArrays(byte[] cipher, byte[] iv) {
        return ByteBuffer.allocate(cipher.length + iv.length)
                .put(cipher)
                .put(iv)
                .array();
    }

    private byte[] getCipherFromJoinByteArray(byte[] byteArray) {
        return Arrays.copyOfRange(byteArray, 0, byteArray.length - IV_SIZE);
    }

    private byte[] getIvFromJoinByteArray(byte[] byteArray) {
        return Arrays.copyOfRange(byteArray, byteArray.length - IV_SIZE, byteArray.length);
    }

}
