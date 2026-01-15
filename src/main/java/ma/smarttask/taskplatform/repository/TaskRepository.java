package ma.smarttask.taskplatform.repository;

import ma.smarttask.taskplatform.model.AbstractTask;
import ma.smarttask.taskplatform.model.StudyTask;
import ma.smarttask.taskplatform.model.enums.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

//intermédiaire entre la couche métier et la base de données
@Repository
public interface TaskRepository extends JpaRepository<AbstractTask, Long> {
    // 1. Genere : SELECT * FROM tasks WHERE completed = false
    Page<AbstractTask> findByCompletedFalse(Pageable pageable);


    // 2. Genere : SELECT * FROM tasks WHERE due_date < ?
    Page<AbstractTask> findByDueDateBefore(LocalDate date, Pageable pageable);

    // 3. Ici on utilise @Query pour etre sur de cibler uniquement les StudyTask
    @Query("SELECT s FROM StudyTask s WHERE s.topic = :topic")
    Page<StudyTask> findStudyTasksByTopic(Topic topic, Pageable pageable);
}
