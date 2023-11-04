package ru.rockutor.editor.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.rockutor.sign.SignTask;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "kafka.enabled")
public class RealKafkaProducerService implements KafkaProducerService {
    private final KafkaTemplate<String, SignTask> kafkaTemplate;

    public void send(String topicName, String key, SignTask task) {
        CompletableFuture<SendResult<String, SignTask>> future =
                kafkaTemplate.send(topicName, key, task);

        future.whenComplete((sendResult, exception) -> {
                   if (exception != null) {
                       log.error(exception.getMessage());
                   } else {
                      future.complete(sendResult);
                   }
                   log.info("Sign task send to topic [{}]", topicName);
                });
    }
}
