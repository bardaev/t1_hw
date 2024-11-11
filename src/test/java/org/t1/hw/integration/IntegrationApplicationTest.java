package org.t1.hw.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.t1.hw.controller.TaskController;
import org.t1.hw.dto.TaskDto;
import org.t1.hw.entity.Status;
import org.t1.hw.entity.TaskEntity;
import org.t1.hw.repository.TaskRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class IntegrationApplicationTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void setup() {
        taskRepository.deleteAll();
    }

    @Test
    public void contextLoads() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertNotNull(webApplicationContext.getBean(TaskController.class));
    }

    @Test
    public void addTask() throws Exception {
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskTitle("title");
        taskDto.setTaskDescription("description");

        mvc
                .perform(
                        post("/tasks")
                        .content(asJsonString(taskDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskTitle").value("title"))
                .andExpect(jsonPath("$.taskDescription").value("description"))
                .andExpect(jsonPath("$.taskStatus").value(Status.IN_PROGRESS.getStatus()));
    }

    @Test
    public void addNullableTask() throws Exception {
        mvc
                .perform(
                        post("/tasks")
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getTask() throws Exception {
        int numberTask1 = 1;
        int numberTask2 = 2;
        Long task1 = addTestTask(numberTask1);
        Long task2 = addTestTask(numberTask2);

        mvc
                .perform(
                        get("/tasks/" + task1)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskTitle").value("title " + numberTask1))
                .andExpect(jsonPath("$.taskDescription").value("description"))
                .andExpect(jsonPath("$.taskStatus").value(Status.IN_PROGRESS.getStatus()));

        mvc
                .perform(
                        get("/tasks/" + task2)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskTitle").value("title " + numberTask2))
                .andExpect(jsonPath("$.taskDescription").value("description"))
                .andExpect(jsonPath("$.taskStatus").value(Status.IN_PROGRESS.getStatus()));
    }

    @Test
    public void getNotExistingTask() throws Exception {
        mvc
                .perform(
                        get("/tasks/123")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllTasks() throws Exception {
        int numberTask1 = 1;
        int numberTask2 = 2;
        int numberTask3 = 3;
        addTestTask(numberTask1);
        addTestTask(numberTask2);
        addTestTask(numberTask3);

        mvc
                .perform(
                        get("/tasks")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].taskTitle").value("title " + numberTask1))
                .andExpect(jsonPath("$.[1].taskTitle").value("title " + numberTask2))
                .andExpect(jsonPath("$.[2].taskTitle").value("title " + numberTask3));
    }

    @Test
    public void updateTask() throws Exception {
        int numberTask1 = 1;
        Long task1 = addTestTask(numberTask1);

        TaskDto updatedTaskDto = new TaskDto();
        updatedTaskDto.setTaskTitle("updatedTitle");
        updatedTaskDto.setTaskDescription("updatedDescription");
        updatedTaskDto.setTaskStatus(Status.FINISHED.getStatus());

        mvc
                .perform(
                        put("/tasks/" + task1)
                        .content(asJsonString(updatedTaskDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskTitle").value("updatedTitle"))
                .andExpect(jsonPath("$.taskDescription").value("updatedDescription"))
                .andExpect(jsonPath("$.taskStatus").value(Status.FINISHED.getStatus()));
    }

    @Test
    public void deleteTask() throws Exception {
        int numberTask1 = 1;
        Long task1 = addTestTask(numberTask1);

        mvc
                .perform(
                        delete("/tasks/" + task1)
                )
                .andDo(print())
                .andExpect(status().isOk());

        mvc
                .perform(
                        get("/tasks/" + task1)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    public Long addTestTask(int numberTask) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle("title " + numberTask);
        taskEntity.setDescription("description");

        return taskRepository.save(taskEntity).getId();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
