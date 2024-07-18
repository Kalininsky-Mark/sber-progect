package repositories;

import tables.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findById(long id);

    List<Task> findByCompletedTrue();

    List<Task> findByCompletedFalse();

    List<Task> findByNameContaining(String part);

    List<Task> findByCompletedTrueAndNameContaining(String part);

    List<Task> findByCompletedFalseAndNameContaining(String part);

}
