package ru.rockutor.auth.filter.token.filter;

import jakarta.servlet.http.HttpServletRequest;
import ru.rockutor.auth.filter.token.AccessTokenVerifier;

public class HeaderAccessTokenFilter extends AccessTokenFilter {
    public HeaderAccessTokenFilter(AccessTokenVerifier accessTokenVerifier) {
        super(accessTokenVerifier);
    }

    @Override
    protected String getAccessToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN);
    }
}
