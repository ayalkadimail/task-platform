package ma.smarttask.taskplatform.repository;

import ma.smarttask.taskplatform.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    // On pourra ajouter plus tard : Optional<AppUser> findByEmail(String email);
}