package ru.rockutor.auth.filter.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.rockutor.auth.AuthResult;
import ru.rockutor.auth.api.AuthApiService;
import ru.rockutor.auth.dto.TokenRs;

@Slf4j
@RequiredArgsConstructor
public class RefreshTokenVerifierImpl implements RefreshTokenVerifier {
    private final AuthApiService authApiService;

    @Override
    public AuthResult<TokenRs> refresh(String refreshToken) {
        try {
            TokenRs tokenRs = authApiService.refresh(refreshToken);
            log.info("Токен обновления подтверждён");
            return AuthResult.authenticated(tokenRs);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.warn("Токен обновления истёк");
            return AuthResult.refreshExpired();
        }
    }
}
