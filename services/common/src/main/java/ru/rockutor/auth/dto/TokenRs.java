package ru.rockutor.auth.dto;

public record TokenRs(String accessToken,
                      String refreshToken,
                      String tokenType) {
}
