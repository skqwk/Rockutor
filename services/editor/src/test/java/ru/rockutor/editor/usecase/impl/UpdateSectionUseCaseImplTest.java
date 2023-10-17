package ru.rockutor.editor.usecase.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.rockutor.editor.domain.Attribute;
import ru.rockutor.editor.domain.AttributeType;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.Section;
import ru.rockutor.editor.repo.DocumentRepo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.rockutor.editor.domain.AttributeType.IMAGE;
import static ru.rockutor.editor.domain.AttributeType.STATIC_TEXT;

class UpdateSectionUseCaseImplTest {
    private static final String TYPE = "type";
    private static final String VALUE = "value";

    private static final UUID DOCUMENT_ID = UUID.randomUUID();
    private static final String SECTION_NAME = "sectionName";
    private static final String ATTRIBUTE_NAME_1 = "attributeName1";
    private static final String ATTRIBUTE_NAME_2 = "attributeName2";

    private final DocumentRepo documentRepo = mock(DocumentRepo.class);
    private final UpdateSectionUseCaseImpl updateSectionUseCase = new UpdateSectionUseCaseImpl(documentRepo);

    @ParameterizedTest
    @MethodSource("throwExceptionWhenNotFoundDataProvider")
    void throwExceptionWhenNotFound(Optional<Document> optionalDocument) {
        // GIVEN
        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(optionalDocument);

        // WHEN | THEN
        assertThrows(NoSuchElementException.class,
                () -> updateSectionUseCase.updateSection(DOCUMENT_ID, SECTION_NAME, Collections.emptyMap()));
    }

    private static Stream<Arguments> throwExceptionWhenNotFoundDataProvider() {
        return Stream.of(
                Arguments.of(Optional.empty()),
                Arguments.of(Optional.of(new Document()))
        );
    }

    @ParameterizedTest
    @MethodSource("updateSectionDataProvider")
    void updateSection(List<Attribute> oldAttributes,
                       Map<String, Map<String, Object>> newAttributes,
                       List<Attribute> expectedAttributes) {
        // GIVEN
        Section section = new Section();
        section.setName(SECTION_NAME);
        section.setAttributes(oldAttributes);

        Document document = new Document();
        document.addSection(section);

        when(documentRepo.findById(DOCUMENT_ID)).thenReturn(Optional.of(document));
        when(documentRepo.save(document)).thenReturn(document);

        // WHEN
        Document actual = updateSectionUseCase.updateSection(DOCUMENT_ID, SECTION_NAME, newAttributes);

        // THEN
        assertSame(document, actual);
        Section actualSection = actual.getSectionByName(SECTION_NAME).get();

        List<Attribute> actualAttributes = actualSection.getAttributes();
        assertAttributesEquals(expectedAttributes, actualAttributes);
    }

    private static Stream<Arguments> updateSectionDataProvider() {
        return Stream.of(
                Arguments.of(Collections.emptyList(), Collections.emptyMap(), Collections.emptyList()),
                Arguments.of(
                        Collections.singletonList(createAttribute(ATTRIBUTE_NAME_1, STATIC_TEXT, "value")),
                        Collections.emptyMap(),
                        Collections.singletonList(createAttribute(ATTRIBUTE_NAME_1, STATIC_TEXT, "value"))
                ),
                Arguments.of(
                        Collections.singletonList(createAttribute(ATTRIBUTE_NAME_1, STATIC_TEXT, "value")),
                        Collections.singletonMap(ATTRIBUTE_NAME_2, createMapAttribute(STATIC_TEXT, "value2")),
                        List.of(
                                createAttribute(ATTRIBUTE_NAME_1, STATIC_TEXT, "value"),
                                createAttribute(ATTRIBUTE_NAME_2, STATIC_TEXT, "value2")
                                )
                ),
                Arguments.of(
                        Collections.emptyList(),
                        Collections.singletonMap(ATTRIBUTE_NAME_2, createMapAttribute(STATIC_TEXT, "value2")),
                        Collections.singletonList(createAttribute(ATTRIBUTE_NAME_2, STATIC_TEXT, "value2"))
                ),
                Arguments.of(
                        Collections.singletonList(createAttribute(ATTRIBUTE_NAME_1, STATIC_TEXT, "value")),
                        Collections.singletonMap(ATTRIBUTE_NAME_1, createMapAttribute(IMAGE, "new_image.png")),
                        Collections.singletonList(createAttribute(ATTRIBUTE_NAME_1, IMAGE, "new_image.png"))
                )
        );
    }

    private static Attribute createAttribute(String name,
                                             AttributeType type,
                                             String value) {
        Attribute attribute = new Attribute();
        attribute.setName(name);
        attribute.setType(type);
        attribute.setValue(value);

        return attribute;
    }

    private static Map<String, Object> createMapAttribute(AttributeType type,
                                                          String value) {
        Map<String, Object> mapAttribute = new HashMap<>(2);
        mapAttribute.put(TYPE, type);
        mapAttribute.put(VALUE, value);

        return mapAttribute;
    }

    private void assertAttributesEquals(List<Attribute> firstAttributes,
                                        List<Attribute> secondAttributes) {
        assertEquals(firstAttributes.size(), secondAttributes.size());

        Map<String, Attribute> firstMappedAttributes = firstAttributes.stream()
                .collect(Collectors.toMap(Attribute::getName, Function.identity()));

        Map<String, Attribute> secondMappedAttributes = secondAttributes.stream()
                .collect(Collectors.toMap(Attribute::getName, Function.identity()));

        assertIterableEquals(firstMappedAttributes.keySet(), secondMappedAttributes.keySet());

        for (String key : firstMappedAttributes.keySet()) {
            Attribute firstAttribute = firstMappedAttributes.get(key);
            Attribute secondAttribute = secondMappedAttributes.get(key);

            assertEquals(firstAttribute.getName(), secondAttribute.getName());
            assertEquals(firstAttribute.getValue(), secondAttribute.getValue());
            assertEquals(firstAttribute.getType(), secondAttribute.getType());
        }
    }
}