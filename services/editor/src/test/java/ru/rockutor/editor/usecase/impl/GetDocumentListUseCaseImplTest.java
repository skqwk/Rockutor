package ru.rockutor.editor.usecase.impl;

import org.junit.jupiter.api.Test;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.repo.DocumentRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetDocumentListUseCaseImplTest {
    private final DocumentRepo documentRepo = mock(DocumentRepo.class);
    private final GetDocumentListUseCaseImpl getDocumentListUseCase = new GetDocumentListUseCaseImpl(documentRepo);

    @Test
    void getDocuments() {
        // GIVEN
        List<Document> documents = new ArrayList<>();
        when(documentRepo.findAll()).thenReturn(documents);

        // WHEN | THEN
        assertSame(documents, getDocumentListUseCase.getDocuments());
    }
}