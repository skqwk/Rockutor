package ru.rockutor.signer.usecase.impl;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.rockutor.sign.SignStatus;
import ru.rockutor.signer.domain.SignRequest;
import ru.rockutor.signer.repo.SignRequestRepo;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateSignRequestUseCaseImplTest {
    private static final UUID DOCUMENT_ID = UUID.randomUUID();
    private static final String AUTHOR = "author";

    private final SignRequestRepo signRequestRepo = mock(SignRequestRepo.class);

    private final CreateSignRequestUseCaseImpl createSignRequestUseCase =
            new CreateSignRequestUseCaseImpl(signRequestRepo);

    private final ArgumentCaptor<SignRequest> signRequestArgumentCaptor = ArgumentCaptor.forClass(SignRequest.class);

    @Test
    void createSignRequest() {
        // GIVEN
        SignRequest expected = new SignRequest();
        when(signRequestRepo.save(signRequestArgumentCaptor.capture())).thenReturn(expected);

        // WHEN
        SignRequest actual = createSignRequestUseCase.createSignRequest(DOCUMENT_ID, AUTHOR);

        // THEN
        assertSame(expected, actual);
        SignRequest captured = signRequestArgumentCaptor.getValue();
        assertEquals(DOCUMENT_ID, captured.getDocumentId());
        assertNotNull(captured.getCreatedAt());
        assertNull(captured.getCanceledAt());
        assertNull(captured.getSignedAt());
        assertEquals(SignStatus.SIGNING, captured.getStatus());
    }
}