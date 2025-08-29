package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskDTO> list(com.example.todo.controller.task.TaskSearchForm searchForm, Model model) {
        return taskService.find(searchForm.toEntity())
                .stream()
                .map(TaskDTO::toDTO)
                .toList();
    }

    @GetMapping("/{id}") // /tasks/${id}
    public TaskDTO showDetail(@PathVariable("id") long taskID, Model model) {
        return taskService.findById(taskID)
                .map(com.example.todo.controller.task.TaskDTO::toDTO)
                .orElseThrow(TaskNotFoundException::new);
    }

    @PostMapping
    public void create(@Validated @RequestBody TaskForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Validation error");
        }
        taskService.create(form.toEntity());
    }

    @PutMapping("{id}")
    public void update(
            @PathVariable("id") long id,
            @Validated @ModelAttribute com.example.todo.controller.task.TaskForm form
    ) {
        var entity = form.toEntity(id);
        taskService.update(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        taskService.delete(id);
    }
}
