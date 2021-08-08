package com.stuartyee.taskmaestro.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

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
	
	public User findUserByUsername(String username) {
		try {
			return uRepo.findByUsername(username);
		} catch(Exception e){
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
	
	
	
	

}
