package ru.rockutor.signer.domain;

import java.util.UUID;

public record RequestCriteria(UUID rqId, UUID documentId) {
}
