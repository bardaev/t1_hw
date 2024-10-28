package org.t1.hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.t1.hw.entity.OutboxPerson;
import org.t1.hw.entity.OutboxPersonStatus;

import java.util.List;

public interface OutboxPersonRepository extends JpaRepository<OutboxPerson, String> {
    List<OutboxPerson> findAllByStatus(OutboxPersonStatus status);
}
