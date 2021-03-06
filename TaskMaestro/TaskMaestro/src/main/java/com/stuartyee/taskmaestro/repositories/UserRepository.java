package com.stuartyee.taskmaestro.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stuartyee.taskmaestro.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public List<User> findAll();
	
	public Optional<User> findById(Long id);
	
	public List<User> findByIdNotIn(List<Long> helperIds); //Want all the users NOT in a list of Helpers associated with a given task
	
	public User findByUsername(String username);
	
}
