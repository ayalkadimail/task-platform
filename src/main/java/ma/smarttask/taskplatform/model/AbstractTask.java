package ma.smarttask.taskplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ma.smarttask.taskplatform.model.enums.Priority;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type" // On ajoute un champ virtuel "type" dans le JSON
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GeneralTask.class, name = "GENERAL"),
        @JsonSubTypes.Type(value = StudyTask.class, name = "STUDY")
})

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Les classes filles partagent cette table
@DiscriminatorColumn(name = "task_type") //Permet à Hibernate de distinguer les sous-types
@Getter @Setter
public abstract class AbstractTask {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(nullable = true)
    private String description; // Nouveau : plus de détails
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private boolean completed = false;
    @CreationTimestamp
    @Column(updatable = false, nullable = false) // Empêche de modifier la date de création par erreur
    private LocalDateTime createdAt;

    private LocalDate dueDate; // Nouveau : l'échéance réelle

    @ManyToOne(optional = false) // Une tache DOIT avoir un owner
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private AppUser owner;

}
