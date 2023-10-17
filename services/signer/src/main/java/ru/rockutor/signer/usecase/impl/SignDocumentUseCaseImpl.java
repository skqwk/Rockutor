package ru.rockutor.signer.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rockutor.sign.SignStatus;
import ru.rockutor.signer.domain.RequestCriteria;
import ru.rockutor.signer.domain.SignRequest;
import ru.rockutor.signer.producer.SignResultProducer;
import ru.rockutor.signer.repo.SignRequestRepo;
import ru.rockutor.signer.usecase.SignDocumentUseCase;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SignDocumentUseCaseImpl implements SignDocumentUseCase {
    private final SignRequestRepo signRequestRepo;
    private final SignResultProducer producer;

    @Override
    @Transactional
    public SignStatus signDocument(RequestCriteria requestCriteria) {
        UUID id = requestCriteria.rqId();
        UUID documentId = requestCriteria.documentId();

        SignRequest signRequest = signRequestRepo.findByIdEqualsOrDocumentIdEquals(id, documentId)
                .orElseThrow();

        signRequest.setSignedAt(Instant.now());
        signRequest.setStatus(SignStatus.SIGNED);

        signRequestRepo.save(signRequest);
        producer.send(signRequest);

        return signRequest.getStatus();
    }
}
