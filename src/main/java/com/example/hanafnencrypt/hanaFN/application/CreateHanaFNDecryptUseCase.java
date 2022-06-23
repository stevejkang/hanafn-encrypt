package com.example.hanafnencrypt.hanaFN.application;

import com.example.hanafnencrypt.hanaFN.constants.HanaFNConstants;
import com.example.hanafnencrypt.hanaFN.dto.DecryptRequest;
import com.example.hanafnencrypt.hanaFN.dto.DecryptResponse;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.util.Base64;

@Service
public class CreateHanaFNDecryptUseCase {
    public DecryptResponse decrypt(DecryptRequest request) {
        String decrypted = createHanaFNDecryption(getEncryptKey(request), request.getString());

        return new DecryptResponse(decrypted);
    }

    private String createHanaFNDecryption(String key, String text) {
        byte[] bDecrypt = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(text));

            byte[] saltBytes = new byte[20];
            buffer.get(saltBytes, 0, saltBytes.length);

            byte[] ivBytes = new byte[cipher.getBlockSize()];
            buffer.get(ivBytes, 0, ivBytes.length);
            byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
            buffer.get(encryptedTextBytes);

            SecretKeySpec secret = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

            bDecrypt = cipher.doFinal(encryptedTextBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(bDecrypt);
    }

    private String getEncryptKey(DecryptRequest request) {
        if (request.getEnvironment().equals("DEV")) {
            return HanaFNConstants.DEV_ENC_KEY + HanaFNConstants.DEV_ENTR_CD + "@@";
        }

        if (request.getEnvironment().equals("PROD")) {
            return HanaFNConstants.PROD_ENC_KEY + HanaFNConstants.PROD_ENTR_CD + "@@";
        }

        return "";
    }
}
