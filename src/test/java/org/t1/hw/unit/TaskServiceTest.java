package org.t1.hw.unit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.t1.hw.dto.TaskDto;
import org.t1.hw.entity.TaskEntity;
import org.t1.hw.repository.TaskRepository;
import org.t1.hw.service.TaskService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    final static Long TASK_ID = 1L;
    final static String TITLE = "title";
    final static String DESCRIPTION = "description";

    final static TaskEntity taskEntity = new TaskEntity();

    @BeforeAll
    static void init() {
        taskEntity.setId(TASK_ID);
        taskEntity.setTitle(TITLE);
        taskEntity.setDescription(DESCRIPTION);
    }

    @BeforeEach
    void setUp() {
        when(taskRepository.findById(anyLong())).thenAnswer(invocation -> {
            Long id = (Long) invocation.getArguments()[0];
            if (id == 1L) {
                return Optional.of(taskEntity);
            } else {
                return Optional.empty();
            }
        });
    }

    @Test
    void getTask() {
        TaskService taskService = new TaskService(taskRepository);
        TaskDto task = taskService.getTask(1L);
        assertNotNull(task);
    }

    @Test
    void getNullTask() {
        TaskService taskService = new TaskService(taskRepository);
        assertThrows(IllegalArgumentException.class, () -> taskService.getTask(2L));
    }

    @Test
    void createTask() {
        reset(taskRepository);
        when(taskRepository.save(any())).thenReturn(taskEntity);

        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(TASK_ID);
        taskDto.setTaskTitle(TITLE);
        taskDto.setTaskDescription(DESCRIPTION);

        TaskService taskService = new TaskService(taskRepository);

        TaskDto returnedTaskDto = taskService.createTask(taskDto);

        assertEquals(taskDto.getTaskId(), returnedTaskDto.getTaskId());
        assertEquals(taskDto.getTaskTitle(), returnedTaskDto.getTaskTitle());
        assertEquals(taskDto.getTaskDescription(), returnedTaskDto.getTaskDescription());
    }

    @Test
    void updateTask() {
        when(taskRepository.save(any())).thenReturn(taskEntity);
        TaskService taskService = new TaskService(taskRepository);

        assertNotNull(taskService.updateTask(1L, new TaskDto()));
    }

    @Test
    void updateNullTask() {
        TaskService taskService = new TaskService(taskRepository);
        assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(2L, new TaskDto()));
    }

    @Test
    void deleteTask() {
        reset(taskRepository);
        TaskService taskService = new TaskService(taskRepository);
        assertDoesNotThrow(() -> taskService.deleteTask(10L));
    }
}