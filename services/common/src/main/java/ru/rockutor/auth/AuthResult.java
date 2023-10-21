package ru.rockutor.auth;

import lombok.Getter;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
public class AuthResult<T> {
    private final AuthStatus status;
    private final T response;

    private AuthResult(AuthStatus status, T response) {
        this.status = status;
        this.response = response;
    }

    public static <T> AuthResult<T> authenticated(T data) {
        return new AuthResult<>(AuthStatus.AUTHENTICATED, data);
    }

    public static <T> AuthResult<T> refreshExpired() {
        return new AuthResult<>(AuthStatus.REFRESH_TOKEN_EXPIRED, null);
    }

    public static <T> AuthResult<T> accessExpired() {
        return new AuthResult<>(AuthStatus.ACCESS_TOKEN_EXPIRED, null);
    }

    public boolean isAccessExpired() {
        return status == AuthStatus.ACCESS_TOKEN_EXPIRED;
    }

    public boolean isRefreshExpired() {
        return status == AuthStatus.REFRESH_TOKEN_EXPIRED;
    }

    public boolean isSuccess() {
        return status == AuthStatus.AUTHENTICATED;
    }
}
