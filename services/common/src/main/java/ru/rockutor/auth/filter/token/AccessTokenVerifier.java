package ru.rockutor.auth.filter.token;

import ru.rockutor.auth.AuthResult;
import ru.rockutor.auth.dto.TokenVerifyRs;

public interface AccessTokenVerifier {
    AuthResult<TokenVerifyRs> verify(String accessToken);
}
