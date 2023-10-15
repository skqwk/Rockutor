package ru.rockutor.signer.usecase;

import ru.rockutor.sign.SignStatus;
import ru.rockutor.signer.domain.RequestCriteria;

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
