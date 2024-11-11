package org.t1.hw.utils;

import org.t1.hw.dto.TaskDto;
import org.t1.hw.entity.Status;
import org.t1.hw.entity.TaskEntity;

public class TaskMapper {

    public static TaskDto toDto(TaskEntity task) {
        if (task == null) throw new IllegalArgumentException("Task is null");
        return TaskDto.builder()
                .taskId(task.getId())
                .taskTitle(task.getTitle())
                .taskDescription(task.getDescription())
                .taskStatus(task.getStatus().toString())
                .build();
    }

    public static TaskEntity toNewEntity(TaskDto dto) {
        if (dto == null) throw new IllegalArgumentException("Dto is null");
        TaskEntity task = new TaskEntity();
        task.setTitle(dto.getTaskTitle());
        task.setDescription(dto.getTaskDescription());
        return task;
    }

    public static void updateEntity(TaskEntity task, TaskDto dto) {
        task.setTitle(dto.getTaskTitle() == null ? task.getTitle() : dto.getTaskTitle());
        task.setDescription(dto.getTaskDescription() == null ? task.getDescription() : dto.getTaskDescription());
        task.setStatus(Status.valueOf(dto.getTaskStatus() == null ? task.getStatus().toString() : dto.getTaskStatus()));
    }
}
