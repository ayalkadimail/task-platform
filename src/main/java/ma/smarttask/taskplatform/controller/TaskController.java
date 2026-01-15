package ma.smarttask.taskplatform.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.smarttask.taskplatform.dto.TaskRequest;
import ma.smarttask.taskplatform.model.AbstractTask;
import ma.smarttask.taskplatform.model.StudyTask;
import ma.smarttask.taskplatform.model.enums.Topic;
import ma.smarttask.taskplatform.service.TaskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService; //Injection par constructeur (via Lombok)

    @GetMapping
    public List<AbstractTask> getAll() {
        return taskService.findAll();
    }
    /* Le client envoie une requête GET=> Le contrôleur appelle le service=> Le service récupère les données
    =>Spring sérialise la liste en JSON*/

    @PostMapping
    public AbstractTask create(@Valid @RequestBody TaskRequest request) {     // Le controller passe juste le "paquet" au service

        return taskService.save(request);
    }
    @GetMapping("/{id}")
    public AbstractTask getById(@PathVariable Long id) {
        // Ici, on appelle le service qui, lui, va jeter l'exception si l'ID n'existe pas
        return taskService.findById(id);
    }


    @GetMapping("/incomplete")
    public List<AbstractTask> getIncomplete() {
        return taskService.findIncompleteTasks();
    }

    @GetMapping("/urgent")
    public List<AbstractTask> getUrgent() {
        return taskService.findUrgentTasks();
    }

    @GetMapping("/study/topic/{topic}")
    public List<StudyTask> getByTopic(@PathVariable Topic topic) {
        return taskService.findStudyTasksByTopic(topic);
    }

    @GetMapping("/study/random")
    public Optional<StudyTask> getRandom() {
        return taskService.findRandomStudyTask();
    }
}
