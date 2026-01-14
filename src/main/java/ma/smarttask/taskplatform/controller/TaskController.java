package ma.smarttask.taskplatform.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.smarttask.taskplatform.dto.TaskRequest;
import ma.smarttask.taskplatform.model.AbstractTask;
import ma.smarttask.taskplatform.service.TaskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
