package ru.rockutor.editor.usecase;

import ru.rockutor.editor.domain.Document;

import java.util.UUID;

/**
 * Use-case удаления атрибута
 */
public interface DeleteAttributeUseCase {
    /**
     * Удалить атрибут
     *
     * @param documentId идентификатор документа
     * @param sectionName название секции
     * @param attributeName название атрибута
     *
     * @return документ
     */
    Document deleteAttribute(UUID documentId,
                             String sectionName,
                             String attributeName);
}
