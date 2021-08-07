package com.stuartyee.taskmaestro.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.stuartyee.taskmaestro.models.User;
import com.stuartyee.taskmaestro.services.MainService;

@Component
public class UserValidator implements Validator{
	
	@Autowired
	MainService mServ;
	
	@Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    // 2
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        
        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            // 3
            errors.rejectValue("passwordConfirmation", "Match", "password does not match");
        }   
        
        if (mServ.usernameExists(user.getUsername())) {
        	errors.rejectValue("username", "Match", "This user already exists");
        }
        
        if(user.getName().contains("<script")) {
        	errors.rejectValue("name", "Forbidden", "Don't do that!");
        }
        
        if(user.getUsername().contains("<script")) {
        	errors.rejectValue("username", "Forbidden", "Don't do that!");
        }
    }

}
