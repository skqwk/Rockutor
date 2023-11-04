package ru.rockutor.loadtesting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class SignerClient {
    private static final String REQUESTS = "/requests";
    private static final String SIGN_DOCUMENT = "/sign";
    private static final String TOKEN = "/token";

    private final RestTemplate restTemplate = new RestTemplate();
    private final User user;
    private final String gateway;

    public List<UUID> getSigningRequests() {
        var headers = getHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(getUrl(REQUESTS),
                HttpMethod.GET,
                request,
                Map.class);
        Map<?, ?> body = response.getBody();
        List<Map> requests = (List<Map>) body.get("requests");
        log.info("Получение запросов на подписание документов [{}]. Код ответа - {}",
                requests.size(), response.getStatusCode().value());
        return requests.stream()
                .filter(rq -> rq.get("status").equals("SIGNING"))
                .map(rq -> rq.get("rqId"))
                .map(Object::toString)
                .map(UUID::fromString).collect(Collectors.toList());
    }

    public void signDocumentByRqId(UUID rqId) {
        var headers = getHeaders();
        HttpEntity<Object> request = new HttpEntity<>(
                Collections.singletonMap("id", rqId),
                headers
        );

        ResponseEntity<Object> response = restTemplate.exchange(getUrl(SIGN_DOCUMENT),
                HttpMethod.POST,
                request,
                Object.class);
        log.info("Подписание документа по запросу [{}]. Код ответа - {}",
                rqId, response.getStatusCode().value());
    }

    public void signDocumentByDocumentId(UUID documentId) {
        var headers = getHeaders();
        HttpEntity<Object> request = new HttpEntity<>(
                Collections.singletonMap("documentId", documentId),
                headers
        );

        ResponseEntity<Object> response = restTemplate.exchange(getUrl(SIGN_DOCUMENT),
                HttpMethod.POST,
                request,
                Object.class);
        log.info("Подписание документа [{}]. Код ответа - {}",
                documentId, response.getStatusCode().value());
    }

    private MultiValueMap<String, String> getHeaders() {
        Map<?, ?> response = restTemplate.postForEntity(getUrl(TOKEN), user, Map.class)
                .getBody();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set("AccessToken", response.get("access_token").toString());
        headers.set("RefreshToken", response.get("refresh_token").toString());

        return headers;
    }

    private String getUrl(String endpoint) {
        return gateway + endpoint;
    }
}
