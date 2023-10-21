package ru.rockutor.auth.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.rockutor.auth.dto.TokenRs;
import ru.rockutor.auth.dto.TokenVerifyRs;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class AuthApiServiceImpl implements AuthApiService {
    private static final String VERIFY = "/verify?token={token}";
    private static final String REFRESH = "/refresh?refresh_token={token}";
    private static final String TOKEN = "token";

    private final RestTemplate restTemplate;
    private final String authApiUrl;

    @Override
    public TokenVerifyRs verify(String accessToken) {
        ResponseEntity<Map> response = restTemplate.postForEntity(
                getUrl(VERIFY),
                null,
                Map.class,
                Collections.singletonMap(TOKEN, accessToken)
        );
        
        log.info("Получен ответ с кодом [{}] от /auth/verify", response.getStatusCode().value());
        
        return TokenVerifyRsConverter.fromMap(response.getBody());
    }

    @Override
    public TokenRs refresh(String refreshToken) {
        ResponseEntity<Map> response = restTemplate.postForEntity(
                getUrl(REFRESH),
                null,
                Map.class,
                Collections.singletonMap(TOKEN, refreshToken));

        log.info("Получен ответ с кодом [{}] от /auth/refresh", response.getStatusCode().value());

        return TokenRsConverter.fromMap(response.getBody());
    }

    private String getUrl(String endpoint) {
        return authApiUrl + endpoint;
    }
}
