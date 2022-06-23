package com.example.hanafnencrypt.hanaFN;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import com.example.hanafnencrypt.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.hanafnencrypt.AcceptanceTestFactory.*;

@DisplayName("HanaFN Encryption/Decryption Test")
class HanaFNAcceptanceTest extends AcceptanceTest {
    @DisplayName("Encryption")
    @Test
    void encrypt() {
        String targetText = "ENCRYPT_TARGET_TEXT";

        ExtractableResponse<Response> 암호화_결과 = hanaFN_암호화_요청(targetText);

        성공_확인(암호화_결과);
    }

    @DisplayName("Decryption")
    @Test
    void decrypt() {
        String encryptedText = "Ob3FRs0TCshSm+2rV4SmgQR6olzLIf/3f47C8Gw7anIus+44wGKzOKJ0711F8tLUQIltYWELwrwT/cb8EIQAaPGsVJA=";
        String originText = "ENCRYPT_TARGET_TEXT";

        ExtractableResponse<Response> 복호화_결과 = hanaFN_복호화_요청(encryptedText);

        성공_확인(복호화_결과);
        hanaFN_암복호화_검증(복호화_결과, originText);
    }
}
