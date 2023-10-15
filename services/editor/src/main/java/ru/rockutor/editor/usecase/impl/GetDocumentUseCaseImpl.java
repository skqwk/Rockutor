package ru.rockutor.editor.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.repo.DocumentRepo;
import ru.rockutor.editor.usecase.GetDocumentUseCase;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetDocumentUseCaseImpl implements GetDocumentUseCase {
    private final DocumentRepo documentRepo;

    @Override
    public Document getDocument(UUID documentId) {
        return documentRepo.findById(documentId).orElseThrow();
    }
}
