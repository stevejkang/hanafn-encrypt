package com.example.hanafnencrypt.hanaFN.presentation;

import com.example.hanafnencrypt.hanaFN.application.CreateHanaFNDecryptUseCase;
import com.example.hanafnencrypt.hanaFN.application.CreateHanaFNEncryptUseCase;
import com.example.hanafnencrypt.hanaFN.dto.DecryptRequest;
import com.example.hanafnencrypt.hanaFN.dto.DecryptResponse;
import com.example.hanafnencrypt.hanaFN.dto.EncryptRequest;
import com.example.hanafnencrypt.hanaFN.dto.EncryptResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HanaFNController {
    private CreateHanaFNEncryptUseCase createHanaFNEncryptUseCase;
    private CreateHanaFNDecryptUseCase createHanaFNDecryptUseCase;

    public HanaFNController(
            CreateHanaFNEncryptUseCase createHanaFNEncryptUseCase,
            CreateHanaFNDecryptUseCase createHanaFNDecryptUseCase
    ) {
        this.createHanaFNEncryptUseCase = createHanaFNEncryptUseCase;
        this.createHanaFNDecryptUseCase = createHanaFNDecryptUseCase;
    }

    @PostMapping("/hana-fn/encryption")
    public ResponseEntity getEncrypted(@RequestBody EncryptRequest encryptRequest) {
        EncryptResponse response = createHanaFNEncryptUseCase.encrypt(encryptRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/hana-fn/decryption")
    public ResponseEntity getDecrypted(@RequestBody DecryptRequest decryptRequest) {
        DecryptResponse response = createHanaFNDecryptUseCase.decrypt(decryptRequest);

        return ResponseEntity.ok(response);
    }
}
