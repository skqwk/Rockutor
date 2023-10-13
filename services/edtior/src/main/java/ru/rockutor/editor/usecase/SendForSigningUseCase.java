package ru.rockutor.editor.usecase;

import java.util.UUID;

/**
 * Use-case отправки документа на подписание
 */
public interface SendForSigningUseCase {
    /**
     * Отправить документ на подписание
     *
     * @param documentId идентификатор документа
     */
    void sendForSigning(UUID documentId);
}
