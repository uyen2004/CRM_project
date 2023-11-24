package vamk.uyen.crm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vamk.uyen.crm.entity.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer>{

}
