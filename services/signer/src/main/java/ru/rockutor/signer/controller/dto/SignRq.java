package ru.rockutor.signer.controller.dto;

import java.util.UUID;

public record SignRq(UUID id, UUID documentId) {
}
