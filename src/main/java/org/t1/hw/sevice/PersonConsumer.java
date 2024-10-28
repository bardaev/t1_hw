package org.t1.hw.sevice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.t1.hw.entity.Person;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersonConsumer {

    private final PersonService personService;

    @KafkaListener(id = "${kafka.consumer.group-id}",
            topics = "${kafka.topic.client_registration}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload List<String> messageList,
                         Acknowledgment ack,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.debug("Person consumer: Обработка новых сообщений");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Person> personList = messageList.stream()
                    .map(item -> {
                        try {
                            Person person = objectMapper.readValue(item, Person.class);
                            person.setId(UUID.fromString(key));
                            return person;
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
            personService.changeOutboxPersonStatus(personService.savePerson(personList));

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            ack.acknowledge();
        }
    }
}
