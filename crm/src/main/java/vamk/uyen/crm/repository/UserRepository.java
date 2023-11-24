package vamk.uyen.crm.repository;

import org.springframework.data.repository.CrudRepository;

import vamk.uyen.crm.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
}
