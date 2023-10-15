package ru.rockutor.editor.usecase.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.DocumentStatus;
import ru.rockutor.editor.repo.DocumentRepo;
import ru.rockutor.editor.usecase.UpdateDocumentStatusUseCase;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateDocumentStatusUseCaseImpl implements UpdateDocumentStatusUseCase {
    private final DocumentRepo documentRepo;

    @Override
    @Transactional
    public DocumentStatus updateStatus(UUID documentId, DocumentStatus newStatus) {
        Document document = documentRepo.findById(documentId).orElseThrow();
        document.setStatus(newStatus);
        documentRepo.save(document);
        log.info("Обновлен статус документа с id=[{}], новый статус=[{}]",
                documentId, newStatus);
        return document.getStatus();
    }
}
