package ru.rockutor.editor.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rockutor.editor.domain.Attribute;
import ru.rockutor.editor.domain.AttributeType;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.Section;
import ru.rockutor.editor.repo.DocumentRepo;
import ru.rockutor.editor.usecase.UpdateSectionUseCase;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UpdateSectionUseCaseImpl implements UpdateSectionUseCase {
    private static final String TYPE = "type";
    private static final String VALUE = "value";

    private final DocumentRepo documentRepo;

    @Override
    public Document updateSection(UUID documentId,
                                  String sectionName,
                                  Map<String, Map<String, Object>> updatedAttributes) {
        Document document = documentRepo.findById(documentId)
                .orElseThrow();

        Section foundedSection = document.getSectionByName(sectionName).orElseThrow();

        Map<String, Attribute> existedAttributes = foundedSection.getAttributes()
                .stream()
                .collect(Collectors.toMap(Attribute::getName, Function.identity()));

        for (String key : updatedAttributes.keySet()) {
            Map<String, Object> params = updatedAttributes.get(key);

            if (existedAttributes.containsKey(key)) {
                Attribute attribute = existedAttributes.get(key);
                fillAttributeFromParams(params, attribute);
            } else {
                Attribute attribute = new Attribute();
                attribute.setName(key);
                fillAttributeFromParams(params, attribute);
                existedAttributes.put(key, attribute);
            }
        }

        foundedSection.setAttributes(new ArrayList<>(existedAttributes.values()));

        return documentRepo.save(document);
    }

    private void fillAttributeFromParams(Map<String, Object> params, Attribute attribute) {
        AttributeType type = AttributeType.valueOf(params.get(TYPE).toString());
        attribute.setType(type);
        attribute.setValue(params.get(VALUE).toString());
    }
}
