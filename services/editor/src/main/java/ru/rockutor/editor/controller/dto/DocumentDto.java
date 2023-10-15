package ru.rockutor.editor.controller.dto;

import java.util.List;
import java.util.UUID;

public record DocumentDto(UUID id,
                          List<SectionDto> sections,
                          String status,
                          String author) {
}
