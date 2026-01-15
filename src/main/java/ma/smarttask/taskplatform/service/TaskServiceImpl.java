package ma.smarttask.taskplatform.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.smarttask.taskplatform.dto.TaskRequest;
import ma.smarttask.taskplatform.exception.ResourceNotFoundException;
import ma.smarttask.taskplatform.model.AbstractTask;
import ma.smarttask.taskplatform.model.GeneralTask;
import ma.smarttask.taskplatform.model.StudyTask;

import ma.smarttask.taskplatform.model.enums.Topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ma.smarttask.taskplatform.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

        task.setCompleted(request.completed() != null ? request.completed() : false);


        // 4. L'ENREGISTREMENT
        return taskRepository.save(task);
    }

    @Override
    public AbstractTask findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache avec l'ID " + id + " introuvable"));
    }

    @Override
    public List<AbstractTask> findIncompleteTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> !task.isCompleted()) // On ne garde que les non terminees
                .toList();
    }
    //********************************************************************************************************
    //streams*************************************************************************************************

    @Override
    public List<AbstractTask> findUrgentTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> task.getDueDate() != null)
                .filter(task -> task.getDueDate().isBefore(LocalDate.now().plusDays(3)))
                .toList();
    }

    @Override
    public List<StudyTask> findStudyTasksByTopic(Topic topic) {
        return taskRepository.findAll().stream()
                .filter(StudyTask.class::isInstance) // Senior tip: plus propre que instanceof
                .map(StudyTask.class::cast)
                .filter(task -> task.getTopic() == topic)
                .toList();
    }

    @Override
    public Optional<StudyTask> findRandomStudyTask() {
        List<StudyTask> studyTasks = taskRepository.findAll().stream()
                .filter(StudyTask.class::isInstance)
                .map(StudyTask.class::cast)
                .toList();

        if (studyTasks.isEmpty()) return Optional.empty();

        int randomIndex = new Random().nextInt(studyTasks.size());
        return Optional.of(studyTasks.get(randomIndex));
    }

    //*******************************
    public Page<AbstractTask> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }
   /* @Override
    public Page<AbstractTask> findIncompleteTasks(Pageable pageable){
        // 1. On recupere TOUTES les taches paginees depuis la DB
        Page<AbstractTask> page= taskRepository.findAll(pageable);
        // 2. On filtre le contenu de cette page avec les Streams
        List<AbstractTask> filterd=page.getContent().stream()
                .filter(task -> !task.isCompleted())
                .toList();
        // 3. On reconstruit la Page
        return new PageImpl<>(filterd,pageable,page.getTotalElements());
    }
    @Override
    public Page<AbstractTask> findUrgentTasks(Pageable pageable) {
        Page<AbstractTask> page = taskRepository.findAll(pageable);
        List<AbstractTask> filtered = page.getContent().stream()
                .filter(task -> task.getDueDate() != null)
                .filter(task -> task.getDueDate().isBefore(LocalDate.now().plusDays(3)))
                .toList();
        return new PageImpl<>(filtered, pageable, page.getTotalElements());
    }
    @Override
    public Page<StudyTask> findStudyTasksByTopic(Topic topic, Pageable pageable) {
        Page<AbstractTask> page = taskRepository.findAll(pageable);
        List<StudyTask> filtered = page.getContent().stream()
                .filter(StudyTask.class::isInstance)
                .map(StudyTask.class::cast)
                .filter(task -> task.getTopic() == topic)
                .toList();
        return new PageImpl<>(filtered, pageable, page.getTotalElements());
    }*/

    @Override
    public Page<AbstractTask> findIncompleteTasks(Pageable pageable) {
        // La base de donnees filtre ET pagine. C'est direct.
        return taskRepository.findByCompletedFalse(pageable);
    }
    @Override
    public Page<AbstractTask> findUrgentTasks(Pageable pageable) {
        // On passe la date limite (J+3) au repository
        return taskRepository.findByDueDateBefore(LocalDate.now().plusDays(3), pageable);
    }
    @Override
    public Page<StudyTask> findStudyTasksByTopic(Topic topic, Pageable pageable) {
        return taskRepository.findStudyTasksByTopic(topic, pageable);
    }

    @Override
    public AbstractTask toggleStatus(Long id) {
        AbstractTask task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache " + id + " introuvable"));

        task.setCompleted(!task.isCompleted()); // On inverse le statut actuel (true -> false, false -> true)

        return taskRepository.save(task);
    }

    @Override
    public AbstractTask update(Long id, TaskRequest request) {
        // 1. Verifier l'existence
        AbstractTask existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache " + id + " introuvable"));

        // 2. Mise a jour des champs communs
        existingTask.setTitle(request.title());
        existingTask.setDescription(request.description());
        existingTask.setPriority(request.priority());
        existingTask.setDueDate(request.dueDate());

        // 3. Mise a jour des champs specifiques (selon le type reel de l'objet)
        if (existingTask instanceof StudyTask studyTask) {
            studyTask.setTopic(request.topic());
            studyTask.setDifficulty(request.difficulty());
        } else if (existingTask instanceof GeneralTask generalTask) {
            generalTask.setCategory(request.category());
        }

        // 4. Sauvegarde
        return taskRepository.save(existingTask);
    }

    @Override
    public void delete(Long id) {
        // 1. Verifier si la tache existe
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : Tache " + id + " introuvable");
        }
        // 2. Supprimer
        taskRepository.deleteById(id);
    }


}
