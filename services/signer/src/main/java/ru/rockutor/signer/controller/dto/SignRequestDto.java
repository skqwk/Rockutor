package ru.rockutor.signer.controller.dto;

import java.util.UUID;

public record SignRequestDto(
        UUID rqId,
        UUID documentId,
        String status,
        String createAt,
        String signedAt,
        String canceledAt) {
}
