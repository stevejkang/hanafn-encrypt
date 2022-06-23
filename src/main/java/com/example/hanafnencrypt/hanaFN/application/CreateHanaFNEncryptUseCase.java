package com.example.hanafnencrypt.hanaFN.application;

import com.example.hanafnencrypt.hanaFN.constants.HanaFNConstants;
import com.example.hanafnencrypt.hanaFN.dto.EncryptRequest;
import com.example.hanafnencrypt.hanaFN.dto.EncryptResponse;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CreateHanaFNEncryptUseCase {
    public EncryptResponse encrypt(EncryptRequest request) {
        String encrypted = createHanaFNEncryption(getEncryptKey(request), request.getString());

        return new EncryptResponse(encrypted);
    }

    private String createHanaFNEncryption(String key, String text) {
        byte[] bEncrypt = null;
        try {
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            byte[] saltBytes = bytes;

            SecretKeySpec secret = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();

            byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();

            byte[] encByte = cipher.doFinal(text.getBytes("UTF-8"));
            bEncrypt = new byte[saltBytes.length + ivBytes.length + encByte.length];
            System.arraycopy(saltBytes, 0, bEncrypt, 0, saltBytes.length);
            System.arraycopy(ivBytes, 0, bEncrypt, saltBytes.length, ivBytes.length);
            System.arraycopy(encByte, 0, bEncrypt, saltBytes.length + ivBytes.length, encByte.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(bEncrypt);
    }

    private String getEncryptKey(EncryptRequest request) {
        if (request.getEnvironment().equals("DEV")) {
            return HanaFNConstants.DEV_ENC_KEY + HanaFNConstants.DEV_ENTR_CD + "@@";
        }

        if (request.getEnvironment().equals("PROD")) {
            return HanaFNConstants.PROD_ENC_KEY + HanaFNConstants.PROD_ENTR_CD + "@@";
        }

        return "";
    }
}
