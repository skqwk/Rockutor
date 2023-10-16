package ru.rockutor.editor.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.Section;
import ru.rockutor.editor.repo.DocumentRepo;
import ru.rockutor.editor.usecase.DeleteSectionUseCase;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DeleteSectionUseCaseImpl implements DeleteSectionUseCase {
    private final DocumentRepo documentRepo;

    @Override
    @Transactional
    public Document deleteSection(UUID documentId, String sectionName) {
        Document document = documentRepo.findById(documentId).orElseThrow();

        List<Section> filteredSections = document.getSections().stream()
                .filter(Predicate.not(section -> section.getName().equals(sectionName)))
                .collect(Collectors.toList());

        document.setSections(filteredSections);
        documentRepo.save(document);

        return document;
    }
}
