package ru.rockutor.signer.usecase;

import ru.rockutor.signer.domain.SignRequest;

import java.util.List;

/**
 * Use-case для получения списка запросов на подписание
 */
public interface GetSignRequestListUseCase {
    /**
     * @return список запросов
     */
    List<SignRequest> getRequests();
}
