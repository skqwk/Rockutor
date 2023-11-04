package ru.rockutor.signer.config;

import ru.rockutor.sign.SignResult;

public interface KafkaProducerService {
    void send(String topicName, String key, SignResult result);
}
