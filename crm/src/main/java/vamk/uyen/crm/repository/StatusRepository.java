package vamk.uyen.crm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vamk.uyen.crm.entity.Status;

@Repository
public interface StatusRepository extends CrudRepository<Status, Integer>{
	
}
