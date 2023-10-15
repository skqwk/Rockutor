package ru.rockutor.signer.usecase.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rockutor.sign.SignStatus;
import ru.rockutor.signer.domain.SignRequest;
import ru.rockutor.signer.repo.SignRequestRepo;
import ru.rockutor.signer.usecase.CreateSignRequestUseCase;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateSignRequestUseCaseImpl implements CreateSignRequestUseCase {
    private final SignRequestRepo signRequestRepo;

    @Override
    @Transactional
    public SignRequest createSignRequest(UUID documentId, String author) {
        SignRequest signRequest = new SignRequest();
        signRequest.setCreatedAt(Instant.now());
        signRequest.setStatus(SignStatus.SIGNING);
        signRequest.setDocumentId(documentId);

        SignRequest saved = signRequestRepo.save(signRequest);
        log.info("Создан запрос id=[{}] на подписание документа id=[{}]",
                saved.getId(), documentId);

        return saved;
    }
}
