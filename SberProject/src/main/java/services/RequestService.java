package services;

import data_transfer.TaskDataTranser;
import tables.Task;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    void save(TaskDataTranser taskDataTranser);

    void update(TaskDataTranser taskDataTranser);

    void delete(Task task);

    Optional<Task> findById(long id);

    List<Task> findAll();

    List<Task> findAllCompleted();

    List<Task> findAllNotCompleted();

    List<Task> findAllContaining(String part);

    List<Task> findAllContainingAndCompleted(String part);

    List<Task> findAllContainingAndNotCompleted(String part);

}
