package ru.rockutor.auth.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class AuthApiConfig {
    @Bean
    public AuthApiService authApiService(RestTemplate restTemplate,
                                         AuthDataConfig dataConfig) {
        return new AuthApiServiceImpl(restTemplate, dataConfig.getAuthApiUrl());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
