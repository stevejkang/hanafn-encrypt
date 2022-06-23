package com.example.hanafnencrypt.hanaFN.dto;

public class EncryptResponse {
    private final String encrypted;

    public EncryptResponse(String encrypted) {
        this.encrypted = encrypted;
    }

    public String getEncrypted() {
        return encrypted;
    }
}
