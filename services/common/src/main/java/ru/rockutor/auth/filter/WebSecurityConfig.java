package ru.rockutor.auth.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.rockutor.auth.api.AuthApiService;
import ru.rockutor.auth.filter.token.AccessTokenVerifier;
import ru.rockutor.auth.filter.token.AccessTokenVerifierImpl;
import ru.rockutor.auth.filter.token.RefreshTokenVerifier;
import ru.rockutor.auth.filter.token.RefreshTokenVerifierImpl;
import ru.rockutor.auth.filter.token.TokenFilterChain;
import ru.rockutor.auth.filter.token.filter.CookieAccessTokenFilter;
import ru.rockutor.auth.filter.token.filter.CookieRefreshTokenFilter;
import ru.rockutor.auth.filter.token.filter.HeaderAccessTokenFilter;
import ru.rockutor.auth.filter.token.filter.HeaderRefreshTokenFilter;
import ru.rockutor.auth.filter.token.filter.TokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthEntryPoint authEntryPoint,
                                           AuthRequestFilter authRequestFilter) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/actuator/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthEntryPoint authEntryPoint() {
        return new AuthEntryPoint();
    }

    @Bean
    public AuthRequestFilter authRequestFilter(TokenFilterChain tokenFilterChain) {
        return new AuthRequestFilter(tokenFilterChain);
    }

    @Bean
    public TokenFilterChain tokenFilterChain(TokenFilter cookieAccessTokenFilter,
                                             TokenFilter cookieRefreshTokenFilter,
                                             TokenFilter headerAccessTokenFilter,
                                             TokenFilter headerRefreshTokenFilter) {
        return new TokenFilterChain()
                .chain(cookieAccessTokenFilter)
                .chain(cookieRefreshTokenFilter)
                .chain(headerAccessTokenFilter)
                .chain(headerRefreshTokenFilter);
    }

    @Bean
    public TokenFilter cookieAccessTokenFilter(AccessTokenVerifier accessTokenVerifier) {
        return new CookieAccessTokenFilter(accessTokenVerifier);
    }

    @Bean
    public TokenFilter headerAccessTokenFilter(AccessTokenVerifier accessTokenVerifier) {
        return new HeaderAccessTokenFilter(accessTokenVerifier);
    }

    @Bean
    public TokenFilter cookieRefreshTokenFilter(AccessTokenVerifier accessTokenVerifier,
                                                RefreshTokenVerifier refreshTokenVerifier) {
        return new CookieRefreshTokenFilter(accessTokenVerifier, refreshTokenVerifier);
    }

    @Bean
    public TokenFilter headerRefreshTokenFilter(AccessTokenVerifier accessTokenVerifier,
                                                RefreshTokenVerifier refreshTokenVerifier) {
        return new HeaderRefreshTokenFilter(accessTokenVerifier, refreshTokenVerifier);
    }

    @Bean
    public AccessTokenVerifier accessTokenVerifier(AuthApiService authApiService) {
        return new AccessTokenVerifierImpl(authApiService);
    }

    @Bean
    public RefreshTokenVerifier refreshTokenVerifier(AuthApiService authApiService) {
        return new RefreshTokenVerifierImpl(authApiService);
    }
}
