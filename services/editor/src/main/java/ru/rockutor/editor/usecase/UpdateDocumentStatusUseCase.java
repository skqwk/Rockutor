package ru.rockutor.editor.usecase;

import ru.rockutor.editor.domain.DocumentStatus;

import java.util.UUID;

/**
 * Use-case обновления статуса документа
 */
public interface UpdateDocumentStatusUseCase {
    /**
     * Обновить статус документа
     *
     * @param documentId идентификатор документа
     * @param newStatus  обновленный статус документа
     *                   
     * @return статус документа после обновления
     */
    DocumentStatus updateStatus(UUID documentId, DocumentStatus newStatus);
}
