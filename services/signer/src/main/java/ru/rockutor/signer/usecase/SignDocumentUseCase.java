package ru.rockutor.signer.usecase;

import ru.rockutor.sign.SignStatus;
import ru.rockutor.signer.domain.RequestCriteria;

/**
 * Use-case для подписания документа
 */
public interface SignDocumentUseCase {
    /**
     * Подписать документ
     *
     * @param requestCriteria критерия поиска документа
     *
     * @return статус документа после подписания
     */
    SignStatus signDocument(RequestCriteria requestCriteria);
}
