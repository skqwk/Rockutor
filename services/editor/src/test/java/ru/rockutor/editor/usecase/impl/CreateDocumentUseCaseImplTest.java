package ru.rockutor.editor.usecase.impl;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.DocumentStatus;
import ru.rockutor.editor.repo.DocumentRepo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateDocumentUseCaseImplTest {
    private static final String AUTHOR = "author";

    private final DocumentRepo documentRepo = mock(DocumentRepo.class);
    private final CreateDocumentUseCaseImpl createDocumentUseCase = new CreateDocumentUseCaseImpl(documentRepo);
    private final ArgumentCaptor<Document> documentCaptor = ArgumentCaptor.forClass(Document.class);


    @Test
    void createDocument() {
        // GIVEN
        Document expected = new Document();
        when(documentRepo.save(documentCaptor.capture())).thenReturn(expected);

        // WHEN
        Document actual = createDocumentUseCase.createDocument(AUTHOR);

        // THEN
        assertSame(expected, actual);
        Document captured = documentCaptor.getValue();
        assertNotNull(captured.getCreatedAt());
        assertNotNull(captured.getSections());
        assertEquals(DocumentStatus.DRAFT, captured.getStatus());
        assertEquals(AUTHOR, captured.getAuthor());
    }
}