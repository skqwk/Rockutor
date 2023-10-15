package ru.rockutor.editor.usecase;

import ru.rockutor.editor.domain.Document;

import java.util.UUID;

/**
 * Use-case получения документа
 */
public interface GetDocumentUseCase {
    /**
     * Получить документ
     *
     * @param documentId идентификатор документа
     *
     * @return документ
     */
    Document getDocument(UUID documentId);
}
