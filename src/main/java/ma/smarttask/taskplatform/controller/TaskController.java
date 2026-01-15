package ma.smarttask.taskplatform.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.smarttask.taskplatform.dto.TaskRequest;
import ma.smarttask.taskplatform.model.AbstractTask;
import ma.smarttask.taskplatform.model.StudyTask;
import ma.smarttask.taskplatform.model.enums.Topic;
import ma.smarttask.taskplatform.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService; //Injection par constructeur (via Lombok)

/*    @GetMapping
    public List<AbstractTask> getAll() {
        return taskService.findAll();
    }
*/
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

/*
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
*/
    @GetMapping("/study/random")
    public Optional<StudyTask> getRandom() {
        return taskService.findRandomStudyTask();
    }

    @GetMapping
    public Page<AbstractTask> getAll(
            @RequestParam(defaultValue = "0") int page,      // Numero de page
            @RequestParam(defaultValue = "5") int size,      // Taille de la page
            @RequestParam(defaultValue = "createdAt,desc") String[] sort // Tri par defaut
    ) {
        // Separation du champ de tri et de la direction (asc/desc)
        Sort.Direction direction = sort[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

        return taskService.findAll(pageable);
    }
    @GetMapping("/incomplete")
    public Page<AbstractTask> getIncomplete(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return taskService.findIncompleteTasks(PageRequest.of(page, size));
    }

    @GetMapping("/urgent")
    public Page<AbstractTask> getUrgent(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return taskService.findUrgentTasks(PageRequest.of(page, size));
    }

    @GetMapping("/study/topic/{topic}")
    public Page<StudyTask> getByTopic(
            @PathVariable Topic topic,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return taskService.findStudyTasksByTopic(topic, PageRequest.of(page, size));
    }

    @PutMapping("/{id}/toggle")
    public AbstractTask toggle(@PathVariable Long id) {
        return taskService.toggleStatus(id);
    }

    @PutMapping("/{id}")
    public AbstractTask update(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        return taskService.update(id, request);
    }

}
