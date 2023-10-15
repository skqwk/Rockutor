package ru.rockutor.signer.usecase;

import ru.rockutor.signer.domain.SignRequest;

import java.util.UUID;

/**
 * Use-case создания запроса на подписание документа
 */
public interface CreateSignRequestUseCase {
    /**
     * Создать запрос на подписание документа
     *
     * @param documentId идентификатора исходного документа
     * @param author автор
     *
     * @return запрос на подписание документа
     */
    SignRequest createSignRequest(UUID documentId, String author);
}
