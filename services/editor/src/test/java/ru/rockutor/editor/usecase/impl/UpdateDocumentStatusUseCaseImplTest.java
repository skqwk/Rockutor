package ru.rockutor.editor.usecase.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.DocumentStatus;
import ru.rockutor.editor.repo.DocumentRepo;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateDocumentStatusUseCaseImplTest {
    private static final UUID DOCUMENT_ID = UUID.randomUUID();

    private final DocumentRepo documentRepo = mock(DocumentRepo.class);

    private final UpdateDocumentStatusUseCaseImpl updateDocumentStatusUseCase =
            new UpdateDocumentStatusUseCaseImpl(documentRepo);

    @Test
    void throwExceptionWhenDocumentNotFound() {
        // GIVEN
        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.empty());

        // WHEN | THEN
        assertThrows(NoSuchElementException.class,
                () -> updateDocumentStatusUseCase.updateStatus(DOCUMENT_ID, DocumentStatus.DRAFT));
    }

    @ParameterizedTest
    @EnumSource(value = DocumentStatus.class)
    void updateStatus(DocumentStatus status) {
        // GIVEN
        Document document = new Document();
        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.of(document));

        // WHEN | THEN
        assertSame(status, updateDocumentStatusUseCase.updateStatus(DOCUMENT_ID, status));
    }
}