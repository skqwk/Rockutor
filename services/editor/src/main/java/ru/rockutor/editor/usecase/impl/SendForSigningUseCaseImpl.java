package ru.rockutor.editor.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.DocumentStatus;
import ru.rockutor.editor.producer.SignTaskProducer;
import ru.rockutor.editor.repo.DocumentRepo;
import ru.rockutor.editor.usecase.SendForSigningUseCase;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SendForSigningUseCaseImpl implements SendForSigningUseCase {
    private final SignTaskProducer producer;
    private final DocumentRepo documentRepo;

    @Override
    @Transactional
    public DocumentStatus sendForSigning(UUID documentId) {
        Document document = documentRepo.findById(documentId).orElseThrow();
        producer.send(document);

        document.setStatus(DocumentStatus.SIGNING);
        documentRepo.save(document);

        return document.getStatus();
    }
}
