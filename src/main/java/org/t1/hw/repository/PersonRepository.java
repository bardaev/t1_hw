package org.t1.hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.t1.hw.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {}