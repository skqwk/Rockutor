package ru.rockutor.editor.usecase;

import ru.rockutor.editor.domain.Document;

public interface CreateDocumentUseCase {
    Document createDocument(String author);
}
