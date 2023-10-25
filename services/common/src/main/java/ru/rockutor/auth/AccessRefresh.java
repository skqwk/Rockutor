package ru.rockutor.auth;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.TokenVerifyRs;
import ru.rockutor.auth.dto.UserData;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Builder
public class AccessRefresh {
    private String access;
    private String refresh;

    private Function<String, AuthResult<TokenVerifyRs>> accessResolver;
    private Function<String, AuthResult<TokenRs>> refreshResolver;

    public boolean auth(Consumer<UserData> authConsumer,
                        Consumer<TokenRs> tokenRsConsumer) {
        if (access == null && refresh == null) {
            return false;
        }

        if (access != null) {
            AuthResult<TokenVerifyRs> accessResult = accessResolver.apply(access);
            if (accessResult.isSuccess()) {
                UserData userData = accessResult.getResponse().userData();
                authConsumer.accept(userData);
                return true;
            }
        }

        if (refresh != null) {
            AuthResult<TokenRs> refreshResult = refreshResolver.apply(refresh);
            if (refreshResult.isSuccess()) {
                TokenRs tokenRs = refreshResult.getResponse();
                tokenRsConsumer.accept(tokenRs);
                AuthResult<TokenVerifyRs> accessResult = accessResolver.apply(tokenRs.accessToken());
                if (accessResult.isSuccess()) {
                    authConsumer.accept(accessResult.getResponse().userData());
                    return true;
                }
            }
            return false;
        }

        return false;
    }
}
