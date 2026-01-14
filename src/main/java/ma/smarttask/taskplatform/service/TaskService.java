package ma.smarttask.taskplatform.service;

import ma.smarttask.taskplatform.dto.TaskRequest;
import ma.smarttask.taskplatform.model.AbstractTask;

import java.util.List;

public interface TaskService {
    AbstractTask findById(Long id);
    List<AbstractTask> findAll();
    AbstractTask save(TaskRequest request);
}
