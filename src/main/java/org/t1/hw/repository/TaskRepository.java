package org.t1.hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.t1.hw.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
