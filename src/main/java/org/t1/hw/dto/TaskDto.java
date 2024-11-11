package org.t1.hw.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long taskId;
    private String taskTitle;
    private String taskDescription;
    private String taskStatus;
}
