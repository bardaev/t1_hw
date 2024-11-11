package org.t1.hw.unit;

import org.junit.jupiter.api.Test;
import org.t1.hw.dto.TaskDto;
import org.t1.hw.entity.Status;
import org.t1.hw.entity.TaskEntity;
import org.t1.hw.utils.TaskMapper;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    @Test
    void toDto() {
        Long id = 1L;
        String title = "title";
        String description = "description";

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);
        taskEntity.setTitle(title);
        taskEntity.setDescription(description);

        TaskDto taskDto = TaskMapper.toDto(taskEntity);
        assertNotNull(taskDto);
        assertEquals(id, taskDto.getTaskId());
        assertEquals(title, taskDto.getTaskTitle());
        assertEquals(description, taskDto.getTaskDescription());
        assertEquals(Status.IN_PROGRESS.toString(), taskDto.getTaskStatus());
    }

    @Test()
    void nullToDto() {
        assertThrows(IllegalArgumentException.class, () -> TaskMapper.toDto(null));
    }

    @Test
    void toNewEntity() {
        Long id = 1L;
        String title = "title";
        String description = "description";

        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(id);
        taskDto.setTaskTitle(title);
        taskDto.setTaskDescription(description);
        taskDto.setTaskStatus(Status.FINISHED.toString());
        TaskEntity taskEntity = TaskMapper.toNewEntity(taskDto);

        assertNotNull(taskEntity);
        assertNull(taskEntity.getId());
        assertEquals(title, taskEntity.getTitle());
        assertEquals(description, taskEntity.getDescription());
        assertEquals(Status.IN_PROGRESS, taskEntity.getStatus());
    }

    @Test
    void nullToNewEntity() {
        assertThrows(IllegalArgumentException.class, () -> TaskMapper.toNewEntity(null));
    }

    @Test
    void updateEntity() {
        String title = "title";
        String description = "description";

        String updatedTitle = "updatedTitle";
        String updatedDescription = "updatedDescription";

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1L);
        taskEntity.setTitle(title);
        taskEntity.setDescription(description);

        TaskDto taskDto = new TaskDto();
        taskDto.setTaskTitle(updatedTitle);
        taskDto.setTaskDescription(updatedDescription);
        taskDto.setTaskStatus(Status.FINISHED.toString());

        TaskMapper.updateEntity(taskEntity, taskDto);

        assertEquals(updatedTitle, taskEntity.getTitle());
        assertEquals(updatedDescription, taskEntity.getDescription());
        assertEquals(Status.FINISHED, taskEntity.getStatus());
    }

    @Test
    void updateEntityFromNullFieldDto() {
        String title = "title";
        String description = "description";

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1L);
        taskEntity.setTitle(title);
        taskEntity.setDescription(description);

        TaskDto taskDto = new TaskDto();

        TaskMapper.updateEntity(taskEntity, taskDto);

        assertEquals(title, taskEntity.getTitle());
        assertEquals(description, taskEntity.getDescription());
        assertEquals(Status.IN_PROGRESS, taskEntity.getStatus());
    }
}