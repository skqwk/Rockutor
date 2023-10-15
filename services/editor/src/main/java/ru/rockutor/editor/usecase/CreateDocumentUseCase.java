package ru.rockutor.editor.usecase;

import ru.rockutor.editor.domain.Document;

/**
 * Use-case создания документа
 */
public interface CreateDocumentUseCase {
    /**
     * Создать документ
     *
     * @param author автор документа
     *
     * @return созданный документ
     */
    Document createDocument(String author);
}
