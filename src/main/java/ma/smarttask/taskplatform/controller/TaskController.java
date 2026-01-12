package ma.smarttask.taskplatform.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ma.smarttask.taskplatform.model.AbstractTask;
import ma.smarttask.taskplatform.model.GeneralTask;
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
    public AbstractTask create(@RequestBody GeneralTask task) { //Désérialise le JSON entrant, Crée automatiquement un objet GeneralTask
        return taskService.save(task);
    }
}
