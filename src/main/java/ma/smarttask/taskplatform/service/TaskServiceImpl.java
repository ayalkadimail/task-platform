package ma.smarttask.taskplatform.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.smarttask.taskplatform.model.AbstractTask;
import ma.smarttask.taskplatform.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    @Override
    public List<AbstractTask> findAll() {
        return taskRepository.findAll();
    }
    @Override
    public AbstractTask save(AbstractTask task) {
        return taskRepository.save(task);
    }
}
