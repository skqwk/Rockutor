package ru.rockutor.auth.filter.token.filter;

import jakarta.servlet.http.HttpServletRequest;
import ru.rockutor.auth.filter.token.AccessTokenVerifier;

public class CookieAccessTokenFilter extends AccessTokenFilter {
    public CookieAccessTokenFilter(AccessTokenVerifier accessTokenVerifier) {
        super(accessTokenVerifier);
    }

    @Override
    protected String getAccessToken(HttpServletRequest request) {
        return CookieHelper.findCookie(request, ACCESS_TOKEN);
    }
}
