package ma.smarttask.taskplatform.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("GENERAL") //Associe cette classe à une valeur précise dans la colonne task_type
//(La valeur discriminante permet d’implémenter le polymorphisme au niveau de la base de données)
public class GeneralTask extends AbstractTask{
    private String category;
}
