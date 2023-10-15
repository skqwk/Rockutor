package ru.rockutor.editor.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rockutor.editor.repo.DocumentRepo;
import ru.rockutor.editor.usecase.DeleteDocumentUseCase;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteDocumentUseCaseImpl implements DeleteDocumentUseCase {
    private final DocumentRepo documentRepo;

    @Override
    @Transactional
    public void deleteDocument(UUID documentId) {
        documentRepo.deleteById(documentId);
    }
}
