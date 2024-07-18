package services;

import data_transfer.TaskDataTranser;
import tables.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final TaskRepository taskRepository;

    @Override
    public void save(TaskDataTranser taskDataTranser) {
        taskRepository.save(convertDataTransferToElements(taskDataTranser));
    }

    @Override
    public void update(TaskDataTranser taskDataTranser) {
        taskRepository.save(convertDataTransferToElements(taskDataTranser));
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public Optional<Task> findById(long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findAllCompleted() {
        return taskRepository.findByCompletedTrue();
    }

    @Override
    public List<Task> findAllNotCompleted() {
        return taskRepository.findByCompletedFalse();
    }

    @Override
    public List<Task> findAllContaining(String part) {
        return taskRepository.findByNameContaining(part);
    }

    @Override
    public List<Task> findAllContainingAndCompleted(String part) {
        return taskRepository.findByCompletedTrueAndNameContaining(part);
    }

    @Override
    public List<Task> findAllContainingAndNotCompleted(String part) {
        return taskRepository.findByCompletedFalseAndNameContaining(part);
    }

    private Task convertDataTransferToElements(TaskDataTranser taskDataTranser) {
        Task task = new Task();

        task.setId(taskDataTranser.getId());
        task.setName(taskDataTranser.getName());
        task.setDescription(taskDataTranser.getDescription());
        task.setExpirationDate(taskDataTranser.getExpirationDate());
        task.setCategorys(taskDataTranser.getCategorys());
        task.setCompleted(taskDataTranser.isCompleted());

        return task;
    }

}
