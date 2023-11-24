package vamk.uyen.crm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vamk.uyen.crm.entity.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer>{

}
