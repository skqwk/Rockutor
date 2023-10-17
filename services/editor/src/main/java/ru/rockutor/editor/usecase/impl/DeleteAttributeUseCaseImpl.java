package ru.rockutor.editor.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rockutor.editor.domain.Attribute;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.Section;
import ru.rockutor.editor.repo.DocumentRepo;
import ru.rockutor.editor.usecase.DeleteAttributeUseCase;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DeleteAttributeUseCaseImpl implements DeleteAttributeUseCase {
    private final DocumentRepo documentRepo;

    @Override
    @Transactional
    public Document deleteAttribute(UUID documentId,
                                    String sectionName,
                                    String attributeName) {
        Document document = documentRepo.findById(documentId).orElseThrow();

        Section section = document.getSectionByName(sectionName).orElseThrow();

        List<Attribute> filteredAttributes = section.getAttributes()
                .stream()
                .filter(Predicate.not(a -> a.getName().equals(attributeName)))
                .collect(Collectors.toList());

        section.setAttributes(filteredAttributes);

        documentRepo.save(document);

        return document;
    }
}
