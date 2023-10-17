package ru.rockutor.editor.usecase.impl;

import org.junit.jupiter.api.Test;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.Section;
import ru.rockutor.editor.repo.DocumentRepo;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateSectionUseCaseImplTest {
    private static final UUID DOCUMENT_ID = UUID.randomUUID();
    private static final String SECTION_NAME = "sectionName";

    private final DocumentRepo documentRepo = mock(DocumentRepo.class);
    private final CreateSectionUseCaseImpl createSectionUseCase = new CreateSectionUseCaseImpl(documentRepo);

    @Test
    void throwExceptionWhenDocumentNotFound() {
        // GIVEN
        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.empty());

        // WHEN | THEN
        assertThrows(NoSuchElementException.class,
                () -> createSectionUseCase.createSection(DOCUMENT_ID, SECTION_NAME));
    }

    @Test
    void throwExceptionWhenSectionAlreadyExists() {
        // GIVEN
        Section section = new Section();
        section.setName(SECTION_NAME);

        Document document = new Document();
        document.setSections(Collections.singletonList(section));

        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.of(document));

        // WHEN
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> createSectionUseCase.createSection(DOCUMENT_ID, SECTION_NAME));

        // THEN
        assertEquals("Секция с названием [sectionName] уже создана", exception.getMessage());
    }

    @Test
    void createSectionUseCase() {
        // GIVEN
        Document document = new Document();

        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.of(document));

        // WHEN
        Document actual = createSectionUseCase.createSection(DOCUMENT_ID, SECTION_NAME);

        // THEN
        assertSame(document, actual);
        Section section = document.getSections().get(0);
        assertEquals(SECTION_NAME, section.getName());

        verify(documentRepo).save(document);
    }
}