package ru.rockutor.editor.usecase;

import ru.rockutor.editor.domain.Document;

import java.util.List;

/**
 * Use-case получения списка документов
 */
public interface GetDocumentListUseCase {
    /**
     * @return список документов
     */
    List<Document> getDocuments();
}
