package com.example.hanafnencrypt.hanaFN.dto;

public class DecryptResponse {
    private final String decrypted;

    public DecryptResponse(String decrypted) {
        this.decrypted = decrypted;
    }

    public String getDecrypted() {
        return decrypted;
    }
}
