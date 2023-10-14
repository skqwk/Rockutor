package ru.rockutor.editor.usecase;

import ru.rockutor.editor.domain.Document;

import java.util.UUID;

public interface GetDocumentUseCase {
    Document getDocument(UUID documentId);
}
