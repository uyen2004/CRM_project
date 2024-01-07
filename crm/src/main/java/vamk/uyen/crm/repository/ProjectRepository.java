package vamk.uyen.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vamk.uyen.crm.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
