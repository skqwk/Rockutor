package ru.rockutor.editor.usecase;

import ru.rockutor.editor.domain.DocumentStatus;

import java.util.UUID;

/**
 * Use-case отправки документа на подписание
 */
public interface SendForSigningUseCase {
    /**
     * Отправить документ на подписание
     *
     * @param documentId идентификатор документа
     *
     * @return статус документа после отправки
     */
    DocumentStatus sendForSigning(UUID documentId);
}
