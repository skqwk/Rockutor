package ru.rockutor.editor.usecase;

import ru.rockutor.editor.domain.Document;

import java.util.UUID;

/**
 * Use-case удаления секции документа
 */
public interface DeleteSectionUseCase {
    /**
     * Удалить секцию
     *
     * @param documentId идентификатор документа
     * @param sectionName название секции
     *
     * @return документ
     */
    Document deleteSection(UUID documentId, String sectionName);
}
