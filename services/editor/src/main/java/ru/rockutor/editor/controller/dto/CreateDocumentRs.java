package ru.rockutor.editor.controller.dto;

import java.util.UUID;

public record CreateDocumentRs(UUID id, String author, String createdAt) {
}
