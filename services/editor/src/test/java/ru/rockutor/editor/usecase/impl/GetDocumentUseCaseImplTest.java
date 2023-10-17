package ru.rockutor.editor.usecase.impl;

import org.junit.jupiter.api.Test;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.repo.DocumentRepo;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetDocumentUseCaseImplTest {
    private static final UUID DOCUMENT_ID = UUID.randomUUID();
    private static final String SECTION_NAME = "sectionName";

    private final DocumentRepo documentRepo = mock(DocumentRepo.class);
    private final GetDocumentUseCaseImpl getDocumentUseCase = new GetDocumentUseCaseImpl(documentRepo);

    @Test
    void throwExceptionWhenDocumentNotFound() {
        // GIVEN
        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.empty());

        // WHEN | THEN
        assertThrows(NoSuchElementException.class,
                () -> getDocumentUseCase.getDocument(DOCUMENT_ID));
    }

    @Test
    void getDocument() {
        // GIVEN
        Document document = new Document();
        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.of(document));

        // WHEN | THEN
        assertSame(document, getDocumentUseCase.getDocument(DOCUMENT_ID));
    }
}