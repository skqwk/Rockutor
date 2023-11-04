package ru.rockutor.editor.config;

import ru.rockutor.sign.SignTask;

public interface KafkaProducerService {
    void send(String topicName, String key, SignTask task);
}
