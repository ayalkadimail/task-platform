package ma.smarttask.taskplatform.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ma.smarttask.taskplatform.model.enums.Category;
import ma.smarttask.taskplatform.model.enums.Difficulty;
import ma.smarttask.taskplatform.model.enums.Priority;
import ma.smarttask.taskplatform.model.enums.Topic;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank(message = "Le titre est obligatoire")
        String title,

        String description,

        @NotNull(message = "La priorite est obligatoire")
        Priority priority,

        @FutureOrPresent(message = "La date ne peut pas etre dans le passe")
        LocalDate dueDate,
        Boolean completed,

        String type, // "GENERAL" ou "STUDY"

        // Champs optionnels selon le type
        Category category,
        Topic topic,
        Difficulty difficulty
) {}