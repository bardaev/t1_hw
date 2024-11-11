package org.t1.hw.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.t1.hw.dto.TaskDto;
import org.t1.hw.entity.TaskEntity;
import org.t1.hw.repository.TaskRepository;
import org.t1.hw.utils.TaskMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;

    public TaskDto getTask(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id).orElse(null);
        return TaskMapper.toDto(taskEntity);
    }

    public List<TaskDto> getTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        return taskEntities.stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    public TaskDto createTask(TaskDto taskDto) {
        TaskEntity taskEntity = TaskMapper.toNewEntity(taskDto);
        TaskEntity savedTask = taskRepository.save(taskEntity);
        return TaskMapper.toDto(savedTask);
    }

    public TaskDto updateTask(Long id, TaskDto taskDto) {
        TaskEntity taskEntity = taskRepository.findById(id).orElse(null);
        if (taskEntity != null) {
            TaskMapper.updateEntity(taskEntity, taskDto);
            taskEntity = taskRepository.save(taskEntity);
            return TaskMapper.toDto(taskEntity);
        } else {
            throw new IllegalArgumentException("Task not found");
        }
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
