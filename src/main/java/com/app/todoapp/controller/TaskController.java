package com.app.todoapp.controller;

import org.springframework.stereotype.Controller;
import com.app.todoapp.services.TaskService;
import org.springframework.ui.Model;
import com.app.todoapp.models.Task;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@Controller
//@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks"; // This should match your HTML template name
    }

    @PostMapping("/")
    public String createTask(@RequestParam String title) {
        taskService.createTasks(title);
        return "redirect:/";
    }

    @GetMapping("/{id}/delete")
    public String deleteTasks(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/toggle")
    public String toggleTasks(@PathVariable Long id) {
        taskService.toggleTasks(id);
        return "redirect:/";
    }


}
