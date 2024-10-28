package org.t1.hw.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.t1.hw.entity.OutboxPerson;
import org.t1.hw.entity.OutboxPersonStatus;
import org.t1.hw.repository.OutboxPersonRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxPersonRegisterer {

    private final PersonProducer personProducer;
    private final OutboxPersonRepository outboxPersonRepository;

    @Async(value = "registerExecutor")
    @Scheduled(fixedDelay = 5000)
    public void registerPerson() {
        log.info("Registering outbox person");
        List<OutboxPerson> outboxPerson = outboxPersonRepository.findAllByStatus(OutboxPersonStatus.IN_ORDER);
        outboxPerson.forEach(person -> personProducer.send(person.getId(), person.getPayload()));
    }

    @Async(value = "outboxPersistExecutor")
    @Scheduled(fixedDelay = 4000)
    public void persistStatusPerson() {
        outboxPersonRepository.deleteAll(outboxPersonRepository.findAllByStatus(OutboxPersonStatus.PERSISTED));
    }
}
