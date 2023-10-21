package ru.rockutor.auth.filter.token.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import ru.rockutor.auth.AuthResult;
import ru.rockutor.auth.dto.TokenVerifyRs;
import ru.rockutor.auth.dto.UserData;
import ru.rockutor.auth.filter.token.AccessTokenVerifier;


@RequiredArgsConstructor
public abstract class AccessTokenFilter implements TokenFilter {
    private final AccessTokenVerifier accessTokenVerifier;

    @Override
    public UserData process(HttpServletRequest request,
                            @NonNull HttpServletResponse response) {
        String accessToken = getAccessToken(request);
        AuthResult<TokenVerifyRs> result = accessTokenVerifier.verify(accessToken);
        if (result.isSuccess()) {
            return result.getResponse().userData();
        }
        return null;
    }

    protected abstract String getAccessToken(HttpServletRequest request);
}
