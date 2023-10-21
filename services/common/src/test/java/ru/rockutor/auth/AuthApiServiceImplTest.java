package ru.rockutor.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import ru.rockutor.auth.api.AuthApiServiceImpl;
import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.TokenVerifyRs;

import java.util.Map;

class AuthApiServiceImplTest {
    /**
     * Ответ на /token из auth-сервиса
     */
    private static final String RESPONSE = """
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzYW0iLCJyb2xlIjoiSU5TUEVDVE9SIiwiZXhwIjoxNjk3ODI5NTk2fQ.nEwCZsCS3xL8chwwx76HOAiSHYe0-4SB1RpIG1rUqDw",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzYW0iLCJyb2xlIjoiSU5TUEVDVE9SIiwiZXhwIjoxNjk3ODI5NzE2fQ.HcpTYFdH3b5z-uyP3Hzq0riwpMUvshmKNljQIlEFaGE",
    "token_type": "bearer"
}
                        """;

    private static final String AUTH_URL = "http://localhost:8012";
    private static final AuthApiServiceImpl authApiService =
            new AuthApiServiceImpl(new RestTemplate(), AUTH_URL);


    public static void main(String[] args) {
        // Проверка интеграции с Auth API /verify
        verify();

        // Проверка интеграции с Auth API /refresh
        refresh();
    }

    static void verify() {
        // GIVEN
        String token = getFromResponse("access_token");

        // WHEN
        TokenVerifyRs tokenVerifyRs = authApiService.verify(token);

        // THEN
        System.out.println(tokenVerifyRs.userData().username());
        System.out.println(tokenVerifyRs.userData().role());
        System.out.println(tokenVerifyRs.message());
        System.out.println(tokenVerifyRs.status());
    }

    static void refresh() {
        // GIVEN
        String refreshToken = getFromResponse("refresh_token");

        // WHEN
        TokenRs tokenRs = authApiService.refresh(refreshToken);

        // THEN
        System.out.println(tokenRs.refreshToken());
        System.out.println(tokenRs.accessToken());
        System.out.println(tokenRs.tokenType());
    }

    private static <T> T getFromResponse(String fieldName) {
        try {
            Map map = new ObjectMapper().readValue(RESPONSE, Map.class);
            return (T) map.get(fieldName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}