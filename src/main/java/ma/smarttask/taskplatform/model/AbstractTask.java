package ma.smarttask.taskplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Les classes filles partagent cette table
@DiscriminatorColumn(name = "task_type") //Permet à Hibernate de distinguer les sous-types
@Getter @Setter
public abstract class AbstractTask {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean completed = false;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
