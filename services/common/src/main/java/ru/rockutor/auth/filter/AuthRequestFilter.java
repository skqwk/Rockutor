package ru.rockutor.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.rockutor.auth.filter.token.TokenFilterChain;

import java.io.IOException;

/**
 * Фильтр для проверки авторизационного токена
 */
@Slf4j
@AllArgsConstructor
public class AuthRequestFilter extends OncePerRequestFilter {
    private final TokenFilterChain tokenFilterChain;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        tokenFilterChain.process(request, response);

        filterChain.doFilter(request, response);
    }
}
