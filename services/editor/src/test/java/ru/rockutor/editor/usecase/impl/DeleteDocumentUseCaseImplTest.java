package ru.rockutor.editor.usecase.impl;

import org.junit.jupiter.api.Test;
import ru.rockutor.editor.repo.DocumentRepo;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeleteDocumentUseCaseImplTest {
    private static final UUID DOCUMENT_ID = UUID.randomUUID();

    private final DocumentRepo documentRepo = mock(DocumentRepo.class);
    private final DeleteDocumentUseCaseImpl deleteDocumentUseCase = new DeleteDocumentUseCaseImpl(documentRepo);

    @Test
    void deleteDocument() {
        // GIVEN | WHEN
        deleteDocumentUseCase.deleteDocument(DOCUMENT_ID);

        // THEN
        verify(documentRepo).deleteById(DOCUMENT_ID);
    }
}