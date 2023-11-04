package ru.rockutor.editor.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.rockutor.sign.SignTask;

@Slf4j
@Service
@ConditionalOnProperty(value = "kafka.enabled", havingValue = "false")
public class StubKafkaProducerService implements KafkaProducerService {
    @Override
    public void send(String topicName, String key, SignTask task) {
        log.info("Вызов заглушки обращения к Kafka");
    }
}
