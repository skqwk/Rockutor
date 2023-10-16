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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteSectionUseCaseImplTest {
    private static final UUID DOCUMENT_ID = UUID.randomUUID();
    private static final String SECTION_NAME = "sectionName";

    private final DocumentRepo documentRepo = mock(DocumentRepo.class);
    private final DeleteSectionUseCaseImpl deleteSectionUseCase = new DeleteSectionUseCaseImpl(documentRepo);

    @Test
    void throwExceptionWhenDocumentNotFound() {
        // GIVEN
        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.empty());

        // WHEN | THEN
        assertThrows(NoSuchElementException.class,
                () -> deleteSectionUseCase.deleteSection(DOCUMENT_ID, SECTION_NAME));
    }

    @Test
    void deleteSection() {
        // GIVEN
        Section section = new Section();
        section.setName(SECTION_NAME);

        Document document = new Document();
        document.setSections(Collections.singletonList(section));

        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.of(document));

        // WHEN
        Document actual = deleteSectionUseCase.deleteSection(DOCUMENT_ID, SECTION_NAME);

        // THEN
        assertSame(document, actual);
        assertIterableEquals(Collections.emptyList(), document.getSections());
    }

    @Test
    void deleteSilentWhenNoSuchSection() {
        // GIVEN
        Document document = new Document();

        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.of(document));

        // WHEN
        Document actual = deleteSectionUseCase.deleteSection(DOCUMENT_ID, SECTION_NAME);

        // THEN
        assertSame(document, actual);
        assertIterableEquals(Collections.emptyList(), document.getSections());
    }
}