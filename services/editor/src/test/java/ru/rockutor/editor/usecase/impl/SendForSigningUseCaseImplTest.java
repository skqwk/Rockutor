package ru.rockutor.editor.usecase.impl;

import org.junit.jupiter.api.Test;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.DocumentStatus;
import ru.rockutor.editor.producer.SignTaskProducer;
import ru.rockutor.editor.repo.DocumentRepo;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SendForSigningUseCaseImplTest {
    private static final UUID DOCUMENT_ID = UUID.randomUUID();

    private final DocumentRepo documentRepo = mock(DocumentRepo.class);
    private final SignTaskProducer signTaskProducer = mock(SignTaskProducer.class);

    private final SendForSigningUseCaseImpl sendForSigningUseCase =
            new SendForSigningUseCaseImpl(signTaskProducer, documentRepo);

    @Test
    void throwExceptionWhenDocumentNotFound() {
        // GIVEN
        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.empty());

        // WHEN | THEN
        assertThrows(NoSuchElementException.class,
                () -> sendForSigningUseCase.sendForSigning(DOCUMENT_ID));
    }


    @Test
    void sendForSigning() {
        // GIVEN
        Document document = new Document();
        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.of(document));

        // WHEN
        DocumentStatus actual = sendForSigningUseCase.sendForSigning(DOCUMENT_ID);

        // THEN
        assertEquals(DocumentStatus.SIGNING, actual);
        verify(signTaskProducer).send(document);
        verify(documentRepo).save(document);
    }
}