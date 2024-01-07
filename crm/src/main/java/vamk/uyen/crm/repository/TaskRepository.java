package vamk.uyen.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vamk.uyen.crm.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
