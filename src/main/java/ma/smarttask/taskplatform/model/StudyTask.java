package ma.smarttask.taskplatform.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ma.smarttask.taskplatform.model.enums.Difficulty;
import ma.smarttask.taskplatform.model.enums.Topic;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("STUDY")
@Getter
@Setter
public class StudyTask extends AbstractTask {
    @Enumerated(EnumType.STRING)
    private Topic topic;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @UpdateTimestamp
    private LocalDate LastReviewedAt;

}
