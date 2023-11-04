package ru.rockutor.signer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.rockutor.sign.SignResult;

@Slf4j
@Service
@ConditionalOnProperty(value = "kafka.enabled", havingValue = "false")
public class StubKafkaProducerService implements KafkaProducerService {
    @Override
    public void send(String topicName, String key, SignResult task) {
        log.info("Вызов заглушки обращения к Kafka");
    }
}
