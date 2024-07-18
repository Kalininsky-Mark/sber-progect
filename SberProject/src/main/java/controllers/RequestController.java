package controllers;

import data_transfer.TaskDataTranser;
import elements.Filters;
import tables.Task;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import services.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/tasks")
    public String tasks(
        @RequestParam(value = "filter", defaultValue = "All") Filters filters,
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
        Model model
    ) {
        model.addAttribute("filter", filters);
        model.addAttribute("keyword", keyword);

        if (keyword.isBlank()) {
            switch (filters) {
                case All -> model.addAttribute("tasks", requestService.findAll());
                case Completed -> model.addAttribute("tasks", requestService.findAllCompleted());
                case NotCompleted -> model.addAttribute("tasks", requestService.findAllNotCompleted());
            }
        } else {
            switch (filters) {
                case All -> model.addAttribute("tasks", requestService.findAllContaining(keyword));
                case Completed -> model.addAttribute("tasks", requestService.findAllContainingAndCompleted(keyword));
                case NotCompleted -> model.addAttribute("tasks", requestService.findAllContainingAndNotCompleted(keyword));
            }
        }

        return "tasks";
    }

    @GetMapping("/tasks/new")
    public String newTaskForm(
        @RequestParam(value = "filter", defaultValue = "All") Filters filters,
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
        Model model
    ) {
        model.addAttribute("filter", filters);
        model.addAttribute("keyword", keyword);
        model.addAttribute("task", new TaskDataTranser());

        return "new_task";
    }

    @PostMapping("/tasks/new")
    public String submitNewTask(
        @RequestParam(value = "filter", defaultValue = "All") Filters filters,
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
        @Valid @ModelAttribute("task") TaskDataTranser taskDataTranser
    ) {
        requestService.save(taskDataTranser);

        return String.format("redirect:/tasks?filter=%s&keyword=%s", filters.name(), keyword);
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTaskForm(
        @PathVariable("id") long id,
        @RequestParam(value = "filter", defaultValue = "All") Filters filters,
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
        Model model
    ) {
        Optional<Task> task = requestService.findById(id);

        if (task.isEmpty()) {
            return String.format("redirect:/tasks?filter=%s&keyword=%s", filters.name(), keyword);
        }

        model.addAttribute("filter", filters);
        model.addAttribute("keyword", keyword);
        model.addAttribute("task", convertEntityToDto(task.get()));

        return "edit_task";
    }

    @PostMapping("/tasks/{id}")
    public String switchTaskCompleteState(
        @PathVariable("id") long id,
        @RequestParam(value = "filter", defaultValue = "All") Filters filters,
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
    ) {
        requestService.findById(id).ifPresent(this::switchTaskState);

        return String.format("redirect:/tasks?filter=%s&keyword=%s", filters.name(), keyword);
    }

    @PostMapping("/tasks/edit/{id}")
    public String editTask(
        @PathVariable("id") long id,
        @RequestParam(value = "filter", defaultValue = "All") Filters filters,
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
        @Valid @ModelAttribute("task") TaskDataTranser taskDataTranser
    ) {
        Optional<Task> task = requestService.findById(id);

        if (task.isEmpty()) {
            return String.format("redirect:/tasks?filter=%s&keyword=%s", filters.name(), keyword);
        }

        taskDataTranser.setId(id);
        requestService.update(taskDataTranser);

        return String.format("redirect:/tasks?filter=%s&keyword=%s", filters.name(), keyword);
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(
        @PathVariable("id") long id,
        @RequestParam(value = "filter", defaultValue = "All") Filters filters,
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
    ) {
        requestService.findById(id).ifPresent(requestService::delete);

        return String.format("redirect:/tasks?filter=%s&keyword=%s", filters.name(), keyword);
    }

    private void switchTaskState(Task task) {
        task.setCompleted(!task.isCompleted());
        requestService.update(convertEntityToDto(task));
    }

    private TaskDataTranser convertEntityToDto(Task task) {
        TaskDataTranser taskDataTranser = new TaskDataTranser();

        taskDataTranser.setId(task.getId());
        taskDataTranser.setName(task.getName());
        taskDataTranser.setDescription(task.getDescription());
        taskDataTranser.setExpirationDate(task.getExpirationDate());
        taskDataTranser.setCategorys(task.getCategorys());
        taskDataTranser.setCompleted(task.isCompleted());

        return taskDataTranser;
    }

}