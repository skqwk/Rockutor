package ru.rockutor.editor.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.repo.DocumentRepo;
import ru.rockutor.editor.usecase.GetDocumentListUseCase;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetDocumentListUseCaseImpl implements GetDocumentListUseCase {
    private final DocumentRepo documentRepo;

    @Override
    public List<Document> getDocuments() {
        return documentRepo.findAll();
    }
}
