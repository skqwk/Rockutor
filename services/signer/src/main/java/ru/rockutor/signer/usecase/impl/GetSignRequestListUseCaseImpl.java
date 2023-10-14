package ru.rockutor.signer.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rockutor.signer.domain.SignRequest;
import ru.rockutor.signer.repo.SignRequestRepo;
import ru.rockutor.signer.usecase.GetSignRequestListUseCase;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetSignRequestListUseCaseImpl implements GetSignRequestListUseCase {
    private final SignRequestRepo signRequestRepo;

    @Override
    public List<SignRequest> getRequests() {
        return signRequestRepo.findAll();
    }
}
