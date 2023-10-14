package ru.rockutor.signer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.rockutor.signer.controller.dto.ListSignRequestRs;
import ru.rockutor.signer.controller.dto.SignRequestDto;
import ru.rockutor.signer.controller.dto.SignRq;
import ru.rockutor.signer.controller.dto.SignStatusRs;
import ru.rockutor.signer.domain.RequestCriteria;
import ru.rockutor.signer.domain.SignRequest;
import ru.rockutor.signer.domain.SignStatus;
import ru.rockutor.signer.usecase.CancelDocumentUseCase;
import ru.rockutor.signer.usecase.GetSignRequestListUseCase;
import ru.rockutor.signer.usecase.SignDocumentUseCase;

import java.util.List;
import java.util.stream.Collectors;

import static ru.rockutor.signer.util.Formatter.format;

@RestController
@RequiredArgsConstructor
public class SignController {
    private final SignDocumentUseCase signDocumentUseCase;
    private final CancelDocumentUseCase cancelDocumentUseCase;
    private final GetSignRequestListUseCase getSignRequestListUseCase;

    @PostMapping("/sign")
    public SignStatusRs signDocument(@RequestBody SignRq signRq) {
        SignStatus signStatus = signDocumentUseCase.signDocument(new RequestCriteria(signRq.id(), signRq.documentId()));
        return new SignStatusRs(signStatus.getLabel(), signStatus.name());
    }

    @PostMapping("/cancel")
    public SignStatusRs cancelDocument(@RequestBody SignRq signRq) {
        SignStatus signStatus = cancelDocumentUseCase.cancelDocument(new RequestCriteria(signRq.id(), signRq.documentId()));
        return new SignStatusRs(signStatus.getLabel(), signStatus.name());
    }

    @GetMapping("/requests")
    public ListSignRequestRs getRequests() {
        List<SignRequest> requests = getSignRequestListUseCase.getRequests();
        return new ListSignRequestRs(toRequestsDto(requests));
    }

    private List<SignRequestDto> toRequestsDto(List<SignRequest> signRequests) {
        return signRequests.stream().map(this::toRequestDto).collect(Collectors.toList());
    }

    private SignRequestDto toRequestDto(SignRequest signRequest) {
        return new SignRequestDto(
                signRequest.getId(),
                signRequest.getDocumentId(),
                signRequest.getStatus().name(),
                format(signRequest.getCreatedAt()),
                format(signRequest.getSignedAt()),
                format(signRequest.getCanceledAt())
        );
    }
}
