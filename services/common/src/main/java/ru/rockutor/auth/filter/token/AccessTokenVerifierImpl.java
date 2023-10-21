package ru.rockutor.auth.filter.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.rockutor.auth.AuthResult;
import ru.rockutor.auth.api.AuthApiService;
import ru.rockutor.auth.dto.TokenVerifyRs;

@Slf4j
@RequiredArgsConstructor
public class AccessTokenVerifierImpl implements AccessTokenVerifier {
    private final AuthApiService authApiService;

    @Override
    public AuthResult<TokenVerifyRs> verify(String accessToken) {
        try {
            TokenVerifyRs tokenVerifyRs = authApiService.verify(accessToken);
            if (tokenVerifyRs.isSuccess()) {
                log.info("Токен доступа верифицирован");
                return AuthResult.authenticated(tokenVerifyRs);
            }
            log.warn("Токен доступа истёк");
            return AuthResult.accessExpired();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return AuthResult.accessExpired();
        }
    }
}
