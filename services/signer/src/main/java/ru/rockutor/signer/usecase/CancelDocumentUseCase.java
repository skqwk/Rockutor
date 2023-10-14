package ru.rockutor.signer.usecase;

import ru.rockutor.signer.domain.RequestCriteria;
import ru.rockutor.signer.domain.SignStatus;

/**
 * Use-case для отмены подписания документа
 */
public interface CancelDocumentUseCase {
    /**
     * Отменить подписание документа
     *
     * @param requestCriteria критерия поиска документа
     *
     * @return статус документа после отмены
     */
    SignStatus cancelDocument(RequestCriteria requestCriteria);
}
