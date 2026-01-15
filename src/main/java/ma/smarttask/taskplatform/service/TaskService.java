package ma.smarttask.taskplatform.service;

import ma.smarttask.taskplatform.dto.TaskRequest;
import ma.smarttask.taskplatform.model.AbstractTask;
import ma.smarttask.taskplatform.model.StudyTask;
import ma.smarttask.taskplatform.model.enums.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
public interface TaskService {
    AbstractTask findById(Long id);
    List<AbstractTask> findAll();
    AbstractTask save(TaskRequest request);

    List<AbstractTask> findIncompleteTasks();
    List<AbstractTask> findUrgentTasks();
    List<StudyTask> findStudyTasksByTopic(Topic topic);
    Optional<StudyTask> findRandomStudyTask();

    Page<AbstractTask> findAll(Pageable pageable);

    Page<AbstractTask> findIncompleteTasks(Pageable pageable);

}
