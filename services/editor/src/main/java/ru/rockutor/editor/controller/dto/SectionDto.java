package ru.rockutor.editor.controller.dto;

import java.util.Map;

public record SectionDto(String name, Map<String, AttributeDto> attributes) {
}
