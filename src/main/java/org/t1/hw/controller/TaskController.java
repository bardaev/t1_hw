package org.t1.hw.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.t1.hw.dto.TaskDto;
import org.t1.hw.service.TaskService;

import java.util.List;

@RestController
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping(path = "/tasks/{id}")
    public TaskDto getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @GetMapping(path = "/tasks")
    public List<TaskDto> getTasks() {
        return taskService.getTasks();
    }

    @PostMapping(value = "/tasks")
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping(value = "/tasks/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return taskService.updateTask(id, taskDto);
    }

    @DeleteMapping(value = "/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(e.getMessage()));
    }
}
