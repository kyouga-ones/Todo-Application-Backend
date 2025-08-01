package com.example.todo.task;

public record TaskEntity(
        Long id,
        String summary,
        String description,
        TaskStatus status
) {
}
