package ru.rockutor.editor.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    private static final String SIGN_TOPIC = "signTopic";

    @Bean
    public NewTopic signTopic() {
        return TopicBuilder.name(SIGN_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
