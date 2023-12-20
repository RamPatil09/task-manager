package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskDto {

    private long id;
    private String title;
    private String description;
    private String dueDate;
    private String assignedTo;
    private String completedDate;
    private String status;
    private String priority;

}
