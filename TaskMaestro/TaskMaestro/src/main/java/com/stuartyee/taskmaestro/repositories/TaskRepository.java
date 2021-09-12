package com.stuartyee.taskmaestro.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stuartyee.taskmaestro.models.Task;
import com.stuartyee.taskmaestro.models.User;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
	
	public List<Task> findAll();
	
	public Optional<Task> findById(Long id); // will need to use .get() method in Service to get Task

	public List<Task> findByCompletedFalse(); //List of tasks not yet completed
	
	public List<Task> findByCompletedTrue(); //List completed tasks
	
	public List<Task> findByCompletedFalseOrderByDueDateAsc(); // Incomplete tasks ordered by due date ASC
	
	public List<Task> findByCompletedFalseOrderByDueDateDesc(); // Incomplete tasks ordered by due date DESC
	
	public List<Task> findByCreator(User creator); //Find tasks created by a given User
	
	public List<Task> findByOwner(User owner); // Find tasks under a given owner
	
	public List<Task> findCompletedTrueByOwner(User owner); //Find all the tasks completed by a given person

	public List<Task> findCompletedFalseByOwner(User owner); //Find all the tasks open by a given owner

	public List<Task> findByIdNotIn(List<Long> helpingTaskIds); //Use a list of task NOT in the many to many User Attribute helpingTasks
	
	
}
