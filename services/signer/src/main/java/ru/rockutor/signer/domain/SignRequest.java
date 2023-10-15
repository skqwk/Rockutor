package ru.rockutor.signer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.rockutor.sign.SignStatus;

import java.time.Instant;
import java.util.UUID;

/**
 * Запрос на подпись документа
 */
@Entity
@Getter
@Setter
@Table(name = "T_SIGN_REQUEST")
public class SignRequest {
    /**
     * Идентификатор запроса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Идентификатор документа
     */
    private UUID documentId;

    /**
     * Дата создания запроса
     */
    private Instant createdAt;

    /**
     * Дата подписания документа
     */
    private Instant signedAt;

    /**
     * Дата отзыва документа
     */
    private Instant canceledAt;

    /**
     * Статус запроса
     */
    private SignStatus status;
}
