package ru.rockutor.auth.filter.token;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.rockutor.auth.UserRole;
import ru.rockutor.auth.dto.UserData;
import ru.rockutor.auth.filter.token.filter.TokenFilter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Setter
public class TokenFilterChain {
    /**
     * Список фильтров токенов
     */
    private final List<TokenFilter> chains = new ArrayList<>();

    public void process(HttpServletRequest request,
                        @NonNull HttpServletResponse response) {
        for (TokenFilter chain : chains) {
            UserData userData = chain.process(request, response);
            if (userData != null) {
                authenticate(userData);
                return;
            }
        }
    }

    public TokenFilterChain chain(TokenFilter tokenFilter) {
        chains.add(tokenFilter);
        return this;
    }

    private void authenticate(UserData userData) {
        UserRole userRole = UserRole.valueOf(userData.role());
        String username = userData.username();

        log.info("Аутентификация пользователя [{}] с ролью [{}]", username, userRole);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                userRole.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
