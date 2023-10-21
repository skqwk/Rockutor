package ru.rockutor.auth.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AuthDataConfig {
    @Value("${auth.api.url}")
    private String authApiUrl;
}
