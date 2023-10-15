package ru.rockutor.editor.usecase;

import java.util.UUID;

/**
 * Use-case удаления документа
 */
public interface DeleteDocumentUseCase {
    /**
     * Удалить документ
     *
     * @param documentId идентификатор документа
     */
    void deleteDocument(UUID documentId);
}
