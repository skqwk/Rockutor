package ru.rockutor.editor.usecase.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.rockutor.editor.domain.Attribute;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.Section;
import ru.rockutor.editor.repo.DocumentRepo;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteAttributeUseCaseImplTest {
    private static final UUID DOCUMENT_ID = UUID.randomUUID();
    private static final String SECTION_NAME = "sectionName";
    private static final String ATTRIBUTE_NAME = "attribute";

    private final DocumentRepo documentRepo = mock(DocumentRepo.class);
    private final DeleteAttributeUseCaseImpl deleteAttributeUseCase = new DeleteAttributeUseCaseImpl(documentRepo);


    @ParameterizedTest
    @MethodSource("throwExceptionWhenNotFoundDataProvider")
    void throwExceptionWhenNotFound(Optional<Document> optionalDocument) {
        // GIVEN
        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(optionalDocument);

        // WHEN | THEN
        assertThrows(NoSuchElementException.class,
                () -> deleteAttributeUseCase.deleteAttribute(DOCUMENT_ID, SECTION_NAME, ATTRIBUTE_NAME));
    }

    private static Stream<Arguments> throwExceptionWhenNotFoundDataProvider() {
        return Stream.of(
                Arguments.of(Optional.empty()),
                Arguments.of(Optional.of(new Document()))
        );
    }

    @ParameterizedTest
    @MethodSource("deleteAttributeDataProvider")
    void deleteAttribute(Document document,
                         List<Attribute> expectedAttributes) {
        // GIVEN
        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.ofNullable(document));

        // WHEN
        Document actual = deleteAttributeUseCase.deleteAttribute(DOCUMENT_ID, SECTION_NAME, ATTRIBUTE_NAME);

        // THEN
        assertSame(document, actual);

        List<Attribute> actualAttributes = actual.getSectionByName(SECTION_NAME)
                .map(Section::getAttributes)
                .orElseGet(Collections::emptyList);

        assertIterableEquals(expectedAttributes, actualAttributes);
    }

    private static Stream<Arguments> deleteAttributeDataProvider() {
        Section emptySection = new Section();
        emptySection.setName(SECTION_NAME);

        Document emptyDocument = new Document();
        emptyDocument.addSection(emptySection);

        Attribute attribute = new Attribute();
        attribute.setName(ATTRIBUTE_NAME);

        Section section = new Section();
        section.setName(SECTION_NAME);
        section.addAttribute(attribute);

        Document document = new Document();
        document.addSection(section);

        return Stream.of(
                Arguments.of(emptyDocument, Collections.emptyList()),
                Arguments.of(document, Collections.emptyList())
        );
    }

}