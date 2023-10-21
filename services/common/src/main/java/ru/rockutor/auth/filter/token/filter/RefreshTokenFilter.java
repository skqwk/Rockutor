package ru.rockutor.auth.filter.token.filter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import ru.rockutor.auth.AuthResult;
import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.TokenVerifyRs;
import ru.rockutor.auth.dto.UserData;
import ru.rockutor.auth.filter.token.AccessTokenVerifier;
import ru.rockutor.auth.filter.token.RefreshTokenVerifier;

@RequiredArgsConstructor
public abstract class RefreshTokenFilter implements TokenFilter {
    private final AccessTokenVerifier accessTokenVerifier;
    private final RefreshTokenVerifier refreshTokenVerifier;

    @Override
    public UserData process(HttpServletRequest request,
                            @NonNull HttpServletResponse response) {
        String refreshToken = getRefreshToken(request);

        AuthResult<TokenRs> refreshResult = refreshTokenVerifier.refresh(refreshToken);
        if (refreshResult.isRefreshExpired()) {
            return null;
        }

        String accessToken = refreshResult.getResponse().accessToken();
        AuthResult<TokenVerifyRs> accessResult = accessTokenVerifier.verify(accessToken);

        if (accessResult.isAccessExpired()) {
            return null;
        }

        fillCookies(response, refreshResult.getResponse());

        return accessResult.getResponse().userData();
    }

    protected abstract String getRefreshToken(HttpServletRequest request);

    private void fillCookies(HttpServletResponse response, TokenRs tokenRs) {
        response.addCookie(new Cookie(REFRESH_TOKEN, tokenRs.refreshToken()));
        response.addCookie(new Cookie(ACCESS_TOKEN, tokenRs.accessToken()));
    }
}
