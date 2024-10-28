package org.t1.hw.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersonProducer {

    @Value("${kafka.topic.client_registration}")
    private String clientRegistrationTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String key, String message) {
        try {
            kafkaTemplate.send(clientRegistrationTopic, key, message).get();
            kafkaTemplate.flush();
            log.info("Sent message to topic {}", clientRegistrationTopic);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    public void send(UUID id) {
        try {
            kafkaTemplate.sendDefault(UUID.randomUUID().toString(), String.valueOf(id)).get();
            kafkaTemplate.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
