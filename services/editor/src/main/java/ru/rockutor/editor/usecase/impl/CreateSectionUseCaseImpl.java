package ru.rockutor.editor.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.Section;
import ru.rockutor.editor.repo.DocumentRepo;
import ru.rockutor.editor.usecase.CreateSectionUseCase;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateSectionUseCaseImpl implements CreateSectionUseCase {
    private final DocumentRepo documentRepo;

    @Override
    @Transactional
    public Document createSection(UUID documentId, String name) {
        Document document = documentRepo.findById(documentId).orElseThrow();

        boolean present = document.getSections().stream()
                .anyMatch(section -> section.getName().equals(name));

        if (present) {
            throw new NoSuchElementException(String.format("Секция с названием [%s] уже создана", name));
        }

        Section section = new Section();
        section.setName(name);

        document.getSections().add(section);
        documentRepo.save(document);

        return document;
    }
}
