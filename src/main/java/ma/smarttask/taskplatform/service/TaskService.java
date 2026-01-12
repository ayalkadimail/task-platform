package ma.smarttask.taskplatform.service;

import ma.smarttask.taskplatform.model.AbstractTask;
import ma.smarttask.taskplatform.model.GeneralTask;

import java.util.List;

public interface TaskService {
    List<AbstractTask> findAll();
    AbstractTask save(GeneralTask task);
}
