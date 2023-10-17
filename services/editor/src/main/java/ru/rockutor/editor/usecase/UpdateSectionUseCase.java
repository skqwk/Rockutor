package ru.rockutor.editor.usecase;

import ru.rockutor.editor.domain.Document;

import java.util.Map;
import java.util.UUID;

/**
 * Use-case обновления секции документа
 */
public interface UpdateSectionUseCase {
    /**
     * Обновить секцию документа
     *
     * @param documentId идентификатор документа
     * @param sectionName название секции
     * @param attributes атрибуты
     *
     * @return документ
     */
    Document updateSection(UUID documentId,
                           String sectionName,
                           Map<String, Map<String, Object>> attributes);
}
