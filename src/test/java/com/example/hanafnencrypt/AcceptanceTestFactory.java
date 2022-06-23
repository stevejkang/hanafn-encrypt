package com.example.hanafnencrypt;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTestFactory {
    public static ExtractableResponse<Response> hanaFN_암호화_요청(String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("string", text);
        params.put("environment", "DEV");

        return RestAssuredTemplate.post("/hana-fn/encryption", params);
    }

    public static ExtractableResponse<Response> hanaFN_복호화_요청(String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("string", text);
        params.put("environment", "DEV");

        return RestAssuredTemplate.post("/hana-fn/decryption", params);
    }

    public static void 성공_확인(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void hanaFN_암복호화_검증(ExtractableResponse<Response> response, String originText) {
        String decryptResponse = response.jsonPath().getString("decrypted");
        assertThat(decryptResponse).isEqualTo(originText);
    }
}
