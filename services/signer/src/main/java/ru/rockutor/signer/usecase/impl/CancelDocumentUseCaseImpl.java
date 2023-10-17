package ru.rockutor.signer.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rockutor.sign.SignStatus;
import ru.rockutor.signer.domain.RequestCriteria;
import ru.rockutor.signer.domain.SignRequest;
import ru.rockutor.signer.producer.SignResultProducer;
import ru.rockutor.signer.repo.SignRequestRepo;
import ru.rockutor.signer.usecase.CancelDocumentUseCase;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CancelDocumentUseCaseImpl implements CancelDocumentUseCase {
    private final SignRequestRepo signRequestRepo;
    private final SignResultProducer producer;

    @Override
    public SignStatus cancelDocument(RequestCriteria requestCriteria) {
        UUID id = requestCriteria.rqId();
        UUID documentId = requestCriteria.documentId();

        SignRequest signRequest = signRequestRepo.findByIdEqualsOrDocumentIdEquals(id, documentId)
                .orElseThrow();

        signRequest.setCanceledAt(Instant.now());
        signRequest.setStatus(SignStatus.CANCELED);

        signRequestRepo.save(signRequest);

        producer.send(signRequest);

        return signRequest.getStatus();
    }
}
