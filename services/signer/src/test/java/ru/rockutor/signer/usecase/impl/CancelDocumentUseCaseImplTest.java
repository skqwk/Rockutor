package ru.rockutor.signer.usecase.impl;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.rockutor.sign.SignStatus;
import ru.rockutor.signer.domain.RequestCriteria;
import ru.rockutor.signer.domain.SignRequest;
import ru.rockutor.signer.producer.SignResultProducer;
import ru.rockutor.signer.repo.SignRequestRepo;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CancelDocumentUseCaseImplTest {
    private static final UUID RQ_ID = UUID.randomUUID();
    private static final UUID DOCUMENT_ID = UUID.randomUUID();


    private final SignRequestRepo signRequestRepo = mock(SignRequestRepo.class);
    private final SignResultProducer signResultProducer = mock(SignResultProducer.class);

    private final CancelDocumentUseCaseImpl cancelDocumentUseCase =
            new CancelDocumentUseCaseImpl(signRequestRepo, signResultProducer);

    private final ArgumentCaptor<SignRequest> signRequestArgumentCaptor = ArgumentCaptor.forClass(SignRequest.class);

    @Test
    void throwExceptionWhenNotFound() {
        // GIVEN
        when(signRequestRepo.findByIdEqualsOrDocumentIdEquals(RQ_ID, DOCUMENT_ID)).thenReturn(Optional.empty());

        // WHEN | THEN
        assertThrows(NoSuchElementException.class,
                () -> cancelDocumentUseCase.cancelDocument(new RequestCriteria(RQ_ID, DOCUMENT_ID)));
    }

    @Test
    void cancelDocument() {
        // GIVEN
        SignRequest signRequest = new SignRequest();
        when(signRequestRepo.findByIdEqualsOrDocumentIdEquals(RQ_ID, DOCUMENT_ID)).thenReturn(Optional.of(signRequest));
        when(signRequestRepo.save(signRequestArgumentCaptor.capture())).thenReturn(signRequest);

        // WHEN
        SignStatus signStatus = cancelDocumentUseCase.cancelDocument(new RequestCriteria(RQ_ID, DOCUMENT_ID));

        // THEN
        SignRequest captured = signRequestArgumentCaptor.getValue();
        assertNotNull(captured.getCanceledAt());
        assertEquals(SignStatus.CANCELED, captured.getStatus());
        assertEquals(SignStatus.CANCELED, signStatus);
        verify(signResultProducer).send(signRequest);
    }
}