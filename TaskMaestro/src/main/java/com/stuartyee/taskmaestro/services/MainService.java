package com.stuartyee.taskmaestro.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.stuartyee.taskmaestro.models.Task;
import com.stuartyee.taskmaestro.models.User;
import com.stuartyee.taskmaestro.repositories.CommentRepository;
import com.stuartyee.taskmaestro.repositories.TaskRepository;
import com.stuartyee.taskmaestro.repositories.UserRepository;

@Service
public class MainService {
	
	UserRepository uRepo;
	TaskRepository tRepo;
	CommentRepository cRepo;
	
	public MainService(UserRepository uRepo, TaskRepository tRepo, CommentRepository cRepo) {
		this.uRepo = uRepo;
		this.tRepo = tRepo;
		this.cRepo = cRepo;
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
	
	
	//Task methods
	public void saveTask(Task task) {
		tRepo.save(task);
	}
	
	public List<Task> findAllTasks(){
		return tRepo.findAll();
	}
	
	public List<Task> findOpenTasksAsc(){
		return tRepo.findByCompletedFalseOrderByDueDateAsc();
	}
	
	public List<Task> findOpenTasksDsc(){
		return tRepo.findByCompletedFalseOrderByDueDateDesc();
	}
	
	
	
	

}
