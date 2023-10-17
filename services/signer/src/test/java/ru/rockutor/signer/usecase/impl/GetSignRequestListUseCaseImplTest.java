package ru.rockutor.signer.usecase.impl;

import org.junit.jupiter.api.Test;
import ru.rockutor.signer.domain.SignRequest;
import ru.rockutor.signer.repo.SignRequestRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetSignRequestListUseCaseImplTest {
    private final SignRequestRepo signRequestRepo = mock(SignRequestRepo.class);

    private final GetSignRequestListUseCaseImpl getSignRequestListUseCase =
            new GetSignRequestListUseCaseImpl(signRequestRepo);

    @Test
    void getRequests() {
        // GIVEN
        List<SignRequest> expected = new ArrayList<>();
        when(getSignRequestListUseCase.getRequests()).thenReturn(expected);

        // WHEN | THEN
        assertEquals(expected, getSignRequestListUseCase.getRequests());
    }
}