package ru.rockutor.auth.filter.token.filter;

import jakarta.servlet.http.HttpServletRequest;
import ru.rockutor.auth.filter.token.AccessTokenVerifier;
import ru.rockutor.auth.filter.token.RefreshTokenVerifier;

public class CookieRefreshTokenFilter extends RefreshTokenFilter {
    public CookieRefreshTokenFilter(AccessTokenVerifier accessTokenVerifier, RefreshTokenVerifier refreshTokenVerifier) {
        super(accessTokenVerifier, refreshTokenVerifier);
    }

    @Override
    protected String getRefreshToken(HttpServletRequest request) {
        return CookieHelper.findCookie(request, REFRESH_TOKEN);
    }
}
