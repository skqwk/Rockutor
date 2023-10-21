package ru.rockutor.auth.api;

import ru.rockutor.auth.dto.TokenRs;

import java.util.Map;

class TokenRsConverter {
    static TokenRs fromMap(Map<?, ?> response) {
        String accessToken = (String) response.get("access_token");
        String refreshToken = (String) response.get("refresh_token");
        String tokenType = (String) response.get("token_type");

        return new TokenRs(accessToken, refreshToken, tokenType);
    }
}
