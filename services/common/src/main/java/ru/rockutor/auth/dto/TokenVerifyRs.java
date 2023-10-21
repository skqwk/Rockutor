package ru.rockutor.auth.dto;

public record TokenVerifyRs(String status,
                            String message,
                            UserData userData) {
    public boolean isSuccess() {
        return "success".equals(status);
    }
}
