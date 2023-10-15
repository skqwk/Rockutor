package ru.rockutor.editor.producer;

import ru.rockutor.editor.domain.Document;

/**
 * Отправитель задачек на подписание документов
 */
public interface SignTaskProducer {
    /**
     * Отправить задачу на подписание документа
     *
     * @param document документ
     */
    void send(Document document);
}
