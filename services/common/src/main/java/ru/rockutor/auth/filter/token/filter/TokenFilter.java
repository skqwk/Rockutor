package ru.rockutor.auth.filter.token.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import ru.rockutor.auth.dto.UserData;

public interface TokenFilter {
    /**
     * Выполнить обработку
     */
    UserData process(HttpServletRequest request,
                     @NonNull HttpServletResponse response);

    String ACCESS_TOKEN = "AccessToken";
    String REFRESH_TOKEN = "RefreshToken";
}
