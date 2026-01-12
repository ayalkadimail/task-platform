package ma.smarttask.taskplatform.repository;

import ma.smarttask.taskplatform.model.AbstractTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//intermédiaire entre la couche métier et la base de données
@Repository
public interface TaskRepository extends JpaRepository<AbstractTask, Long> {
}
