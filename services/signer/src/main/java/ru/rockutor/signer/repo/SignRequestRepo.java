package ru.rockutor.signer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rockutor.signer.domain.SignRequest;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с {@link SignRequest запросом на подписания}
 */
public interface SignRequestRepo extends JpaRepository<SignRequest, UUID> {
    /**
     * Найти запрос на подписание по идентификатору документа или идентификатору запроса
     *
     * @param id идентификатор запроса
     * @param documentId идентификатор документ
     *
     * @return запрос на подписание
     */
    Optional<SignRequest> findByIdEqualsOrDocumentIdEquals(UUID id,
                                                           UUID documentId);
}
