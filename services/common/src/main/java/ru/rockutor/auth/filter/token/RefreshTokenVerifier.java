package ru.rockutor.auth.filter.token;

import ru.rockutor.auth.AuthResult;
import ru.rockutor.auth.dto.TokenRs;

public interface RefreshTokenVerifier {
    AuthResult<TokenRs> refresh(String refreshToken);
}
