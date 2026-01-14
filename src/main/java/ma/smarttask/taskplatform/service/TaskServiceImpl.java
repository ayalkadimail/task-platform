package ma.smarttask.taskplatform.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.smarttask.taskplatform.dto.TaskRequest;
import ma.smarttask.taskplatform.exception.ResourceNotFoundException;
import ma.smarttask.taskplatform.model.AbstractTask;
import ma.smarttask.taskplatform.model.GeneralTask;
import ma.smarttask.taskplatform.model.StudyTask;

import ma.smarttask.taskplatform.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    @Override
    public List<AbstractTask> findAll() {
        return taskRepository.findAll();
    }
    @Override
    public AbstractTask save(TaskRequest request) {
        AbstractTask task; // 1. On prepare une variable vide de type "Tache"

        // 2. LE CHOIX DU TYPE (Logique spécifique)
        if ("STUDY".equalsIgnoreCase(request.type())) {
            StudyTask studyTask = new StudyTask(); // On cree l'objet reel
            studyTask.setTopic(request.topic());   // On remplit ce qui est propre aux etudes
            studyTask.setDifficulty(request.difficulty());
            task = studyTask; // On la range dans notre variable "task"
        } else {
            GeneralTask generalTask = new GeneralTask(); // On cree l'objet reel
            generalTask.setCategory(request.category()); // On remplit ce qui est propre au general
            task = generalTask; // On la range dans notre variable "task"
        }

        // 3. LE REMPLISSAGE COMMUN (Grace a l'heritage)
        // Ici, on ne se soucie plus de savoir si c'est STUDY ou GENERAL.
        // Puisque les deux sont des "AbstractTask", elles ont forcement un titre, etc.
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setPriority(request.priority());
        task.setDueDate(request.dueDate());

        // 4. L'ENREGISTREMENT
        return taskRepository.save(task);
    }

    @Override
    public AbstractTask findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache avec l'ID " + id + " introuvable"));
    }
}
