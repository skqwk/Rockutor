package ru.rockutor.auth.api;

import ru.rockutor.auth.dto.TokenVerifyRs;
import ru.rockutor.auth.dto.UserData;

import java.util.Map;

class TokenVerifyRsConverter {
    static TokenVerifyRs fromMap(Map<?, ?> response) {
        String status = (String) response.get("status");
        String message = (String) response.get("message");
        UserData userData = UserDataConverter.fromMap((Map) response.get("data"));

        return new TokenVerifyRs(status, message, userData);
    }
}
