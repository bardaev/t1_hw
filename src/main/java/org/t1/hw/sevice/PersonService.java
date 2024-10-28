package org.t1.hw.sevice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import org.t1.hw.entity.OutboxPerson;
import org.t1.hw.entity.OutboxPersonStatus;
import org.t1.hw.entity.Person;
import org.t1.hw.repository.OutboxPersonRepository;
import org.t1.hw.repository.PersonRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final OutboxPersonRepository outboxPersonRepository;
    private final PersonRepository personRepository;
    private final PersonProducer personProducer;

    public void savePersonsOutbox(int count) {
        List<OutboxPerson> persons = generatePersonData(count);
        outboxPersonRepository.saveAll(persons);
    }

    public List<Person> savePerson(List<Person> persons) {
        log.info("Saving {} persons", persons.size());
        List<Person> personList = personRepository.saveAll(persons);
        personList
                .stream()
                .map(Person::getId)
                .forEach(personProducer::send);
        return personList;
    }

    public void changeOutboxPersonStatus(List<Person> persons) {
        persons.forEach(person -> outboxPersonRepository
                .findById(String.valueOf(person.getId()))
                .ifPresent(outboxPerson -> {
                    outboxPerson.setStatus(OutboxPersonStatus.PERSISTED);
                    outboxPersonRepository.save(outboxPerson);
                }));
    }

    private List<OutboxPerson> generatePersonData(int count) {
        List<OutboxPerson> persons = new ArrayList<>();
        Faker faker = new Faker();
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < count; i++) {
            OutboxPerson person = new OutboxPerson();

            Person personEntity = new Person();
            personEntity.setFirstName(faker.name().firstName());
            personEntity.setLastName(faker.name().lastName());
            personEntity.setNickname(faker.funnyName().name());

            try {
                person.setPayload(mapper.writeValueAsString(personEntity));
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }

            persons.add(person);
        }
        return Collections.unmodifiableList(persons);
    }
}
