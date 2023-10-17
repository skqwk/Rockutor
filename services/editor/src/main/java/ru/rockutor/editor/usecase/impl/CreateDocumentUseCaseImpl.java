package ru.rockutor.editor.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.DocumentStatus;
import ru.rockutor.editor.repo.DocumentRepo;
import ru.rockutor.editor.usecase.CreateDocumentUseCase;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CreateDocumentUseCaseImpl implements CreateDocumentUseCase {
    private final DocumentRepo documentRepo;

    @Override
    @Transactional
    public Document createDocument(String author) {
        Document document = new Document();
        document.setCreatedAt(Instant.now());
        document.setStatus(DocumentStatus.DRAFT);
        document.setAuthor(author);

        return documentRepo.save(document);
    }
}
