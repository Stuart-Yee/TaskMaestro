package com.stuartyee.taskmaestro.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.ZonedDateTime;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stuartyee.taskmaestro.models.Comment;
import com.stuartyee.taskmaestro.models.Task;
import com.stuartyee.taskmaestro.models.User;
import com.stuartyee.taskmaestro.repositories.CommentRepository;
import com.stuartyee.taskmaestro.repositories.TaskRepository;
import com.stuartyee.taskmaestro.repositories.UserRepository;

@Service
@Transactional
public class MainService {
	
	UserRepository uRepo;
	TaskRepository tRepo;
	CommentRepository cRepo;
	
	public MainService(UserRepository uRepo, TaskRepository tRepo, CommentRepository cRepo) {
		this.uRepo = uRepo;
		this.tRepo = tRepo;
		this.cRepo = cRepo;
	}
	
	//Helper function to convert dates to Month Day, Year format (September 12, 2021)
	public String convertDate(Date date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
		String formattedDate = localDate.format(formatter);
		return formattedDate;
	}
	
	//Helper method to get now
	public Date getNow() {
		LocalDate now = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = now.atStartOfDay(defaultZoneId);
		Date today = Date.from(zonedDateTime.toInstant());
		return today;
		
		
	}
		
	
	
	//User methods
	public List<User> findAllUsers(){
		return uRepo.findAll();
	}
	
	public User findUserByUsername(String username) {
		try {
			return uRepo.findByUsername(username);
		} catch(Exception e){
			return null;
		}
	}
	
	public User findUserbyId(Long Id) {
		if(uRepo.findById(Id).isPresent()) {
			return uRepo.findById(Id).get();
		} else {
			return null;
		}
		
	}
	
	public boolean usernameExists(String username) {
		if(findUserByUsername(username) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void saveUser(User user) {
		String hashpassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashpassword);
		uRepo.save(user);
	}
	
	public void updateUser(User user) {
		uRepo.save(user);
	}
	
	public boolean authenticateUser(String username, String password) {
		User user = uRepo.findByUsername(username);
		if(user == null) {
			return false;
		} else {
			if(BCrypt.checkpw(password, user.getPassword())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public List<User> findNotHelping(Task task) {
		List<Long> userIds = new ArrayList<>();
		for(User user : task.getHelpers()) {
			userIds.add(user.getId());
		}
		userIds.add(task.getOwner().getId());
		if(userIds.isEmpty()) {
			return uRepo.findAll();
		} else {
			return uRepo.findByIdNotIn(userIds);
		}
	}
	
	
	//Task methods
	public void saveTask(Task task) {
		tRepo.save(task);
	}
	
	public List<Task> findAllTasks(){
		List<Task> tasks = tRepo.findAll();
		for (Task task : tasks) {
			if (task.getCompletedOn() == null) {
				task.setFormattedCompletedDate("");
			} else {
				task.setFormattedCompletedDate(convertDate(task.getCompletedOn()));
			}
			
			task.setFormattedDueDate(convertDate(task.getDueDate()));
			task.setFormattedCreatedDate(convertDate(task.getCreatedAt()));
		}
		return tasks;
	}
	
	public List<Task> findOpenTasksAsc(){
		List<Task> tasks = tRepo.findByCompletedFalseOrderByDueDateAsc();
		for (Task task : tasks) {
			task.setFormattedDueDate(convertDate(task.getDueDate()));
			task.setFormattedCreatedDate(convertDate(task.getCreatedAt()));
		}
		return tasks;
	}
	
	public List<Task> findOpenTasksDsc(){
		List<Task> tasks = tRepo.findByCompletedFalseOrderByDueDateDesc();
		for (Task task : tasks) {
			task.setFormattedCreatedDate(convertDate(task.getCreatedAt()));
			task.setFormattedDueDate(convertDate(task.getDueDate()));
		}
		return tasks;
	}
	
	public Task findTaskById(Long id) {
		Task task = tRepo.findById(id).orElse(null);
		if (task.getCompletedOn() != null) {
			task.setFormattedCompletedDate(convertDate(task.getCompletedOn()));
		} else {
			task.setFormattedCompletedDate("");
		}
		task.setFormattedCreatedDate(convertDate(task.getCreatedAt()));
		task.setFormattedDueDate(convertDate(task.getDueDate()));		
		return task; 
	}
	
	public void completeTask(Task task) {
		task.setCompletedOn(getNow());
		task.setCompleted(true);
		tRepo.save(task);
	}
	
	// Comment Methods
	public void saveComment(String content, User user, Task task) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setCommenter(user);
		comment.setParentTask(task);
		cRepo.save(comment);
	}
	
	public void updateComment(Comment comment) {
		cRepo.save(comment);
	}
	
	public Comment findCommentById(Long id) {
		return cRepo.findById(id).orElse(null);
	}
	
	public void deleteComment(Comment comment, User user) {
		if(user.getId() == comment.getCommenter().getId()) {
			cRepo.delete(comment);
		}
	}
	
	public List<Comment> findCommentsByTask(Task task){
		List<Comment> comments = cRepo.findByParentTask(task);
		for(Comment comment: comments) {
			comment.setFormattedCreatedDate(convertDate(comment.getCreatedAt()));
		}
		return comments;
	}
	
	//List comments liked by the user in the current task
	public List<Comment> findLikedCommentsUnderTask(User user, Task task){
		List<Comment> theseComments = cRepo.findByParentTask(task);
		List<Comment> likedComments = new ArrayList<>();
		for (Comment comment : theseComments) {
			if(user.getLikedComments().contains(comment)) {
				likedComments.add(comment);
			}
		}
		return likedComments;
		
	}
	
	// Like Methods
	public boolean likedByUser(User user, Comment comment) {
		System.out.println("Liked by user method invoked");
		System.out.println("User: " + user);
		for(Comment liked: user.getLikedComments()) {
			System.out.println(liked);
		}
		if(user.getLikedComments().contains(comment)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void likeComment(User user, Comment comment) {
		comment.getLikers().add(user);
		comment.setNumberOfLikes(comment.getLikers().size());
		cRepo.save(comment);
		uRepo.save(user);
	}
	
	public void unLikeComment(User user, Comment comment) {

		comment.getLikers().remove(findUserbyId(user.getId()));
		
		
		comment.setNumberOfLikes(comment.getLikers().size());
		cRepo.save(comment);
		uRepo.save(user);
	}

	
	
	
	

}
