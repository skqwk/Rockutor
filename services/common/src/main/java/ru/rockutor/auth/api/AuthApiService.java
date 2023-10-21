package ru.rockutor.auth.api;

import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.TokenVerifyRs;

/**
 * API взаимодействия с сервисом авторизации и аутентификации
 */
public interface AuthApiService {
    /**
     * Проверка токена доступа
     *
     * @param accessToken токен доступа
     *
     * @return результат проверки токена доступа
     */
    TokenVerifyRs verify(String accessToken);

    /**
     * Обновление токена
     *
     * @param refreshToken токен обновления
     *
     * @return обновленная информация
     */
    TokenRs refresh(String refreshToken);
}
