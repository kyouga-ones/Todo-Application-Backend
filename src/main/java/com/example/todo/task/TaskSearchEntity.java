package com.example.todo.task;

import java.util.List;

public record TaskSearchEntity(
        String summary,
        List<TaskStatus> status
) {
}
