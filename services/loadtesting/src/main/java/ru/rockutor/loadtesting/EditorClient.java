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
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class EditorClient {
    private static final String DOCUMENTS = "/documents";
    private static final String DOCUMENTS_ID = "/documents/{documentId}";
    private static final String SEND_FOR_SIGNING = "/sendForSigning/{documentId}";
    private static final String SECTION = "/documents/{documentId}/section/{sectionName}";
    private static final String TOKEN = "/token";

    private final RestTemplate restTemplate = new RestTemplate();
    private final User user;
    private final String gateway;

    public UUID createDocument() {
        var headers = getHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(getUrl(DOCUMENTS), request, Map.class);
        String documentId = response.getBody().get("id").toString();

        log.info("Создание документа [{}]. Код ответа - {}", documentId, response.getStatusCode().value());

        return UUID.fromString(documentId);
    }

    public void createSection(UUID documentId, String sectionName) {
        var headers = getHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(getUrl(SECTION), request,
                Map.class,
                Map.of("documentId", documentId, "sectionName", sectionName));

        log.info("Создание секции [{}] для документа [{}]. Код ответа - {}",
                sectionName, documentId, response.getStatusCode().value());
    }

    public void deleteDocument(UUID documentId) {
        var headers = getHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(getUrl(DOCUMENTS_ID),
                HttpMethod.DELETE,
                request,
                Object.class,
                Map.of("documentId", documentId));

        log.info("Удаление документа [{}]. Код ответа - {}", documentId, response.getStatusCode().value());
    }

    public void deleteSection(UUID documentId, String sectionName) {
        var headers = getHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(getUrl(SECTION),
                HttpMethod.DELETE,
                request,
                Object.class,
                Map.of("documentId", documentId, "sectionName", sectionName));

        log.info("Удаление секции [{}] документа [{}]. Код ответа - {}",
                sectionName, documentId, response.getStatusCode().value());
    }

    public void addAttribute(UUID documentId, String sectionName, Attribute attribute) {
        var headers = getHeaders();

        Map<String, Object> data = Collections.singletonMap("attributes",
                Collections.singletonMap(attribute.getName(),
                        Map.of("type", attribute.getType(),"value", attribute.getValue()
                        )
                )
        );
        HttpEntity<Object> request = new HttpEntity<>(data, headers);

        ResponseEntity<Object> response = restTemplate.exchange(getUrl(SECTION),
                HttpMethod.PUT,
                request,
                Object.class,
                Map.of("documentId", documentId, "sectionName", sectionName));

        log.info("Создание атрибута [{}] в документе [{}] в секции [{}]. Код ответа - {}",
                attribute.getName(), documentId, sectionName, response.getStatusCode().value());
    }

    public void sendForSigning(UUID documentId) {
        var headers = getHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.postForEntity(getUrl(SEND_FOR_SIGNING), request, Object.class,
                Map.of("documentId", documentId));

        log.info("Отправка на подписание документа [{}]. Код ответа - {}", documentId, response.getStatusCode().value());
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
