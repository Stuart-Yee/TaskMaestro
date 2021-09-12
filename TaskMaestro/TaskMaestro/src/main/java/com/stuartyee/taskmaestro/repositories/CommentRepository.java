package com.stuartyee.taskmaestro.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stuartyee.taskmaestro.models.Comment;
import com.stuartyee.taskmaestro.models.Task;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	public List<Comment> findAll();
	
	public List<Comment> findByParentTask(Task task);
	
	public Optional<Comment> findById(Long id);
	
	public List<Comment> findByIdInOrderByNumberOfLikesAsc(List<Long> ids); //List of Ids from parent object (Comment or Task)
	
	public List<Comment> findByIdInOrderByNumberOfLikesDesc(List<Long> ids);


}
