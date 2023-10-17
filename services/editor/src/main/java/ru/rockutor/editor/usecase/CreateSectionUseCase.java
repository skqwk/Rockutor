package ru.rockutor.editor.usecase;

import ru.rockutor.editor.domain.Document;

import java.util.UUID;

/**
 * Use-case создания секции документа
 */
public interface CreateSectionUseCase {
    /**
     * Создать секцию документа
     *
     * @param documentId идентификатор документа
     * @param name название секции
     *
     * @return документ
     */
    Document createSection(UUID documentId, String name);
}
