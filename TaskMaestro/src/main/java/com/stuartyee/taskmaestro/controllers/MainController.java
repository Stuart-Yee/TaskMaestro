package com.stuartyee.taskmaestro.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stuartyee.taskmaestro.models.User;
import com.stuartyee.taskmaestro.services.MainService;
import com.stuartyee.taskmaestro.validators.UserValidator;

@Controller
public class MainController {
	
	@Autowired
	MainService mServ;
	@Autowired 
	UserValidator uVal;
	
	@GetMapping("/login")
	public String loginPage() {
		return "login.jsp";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String passwordEntry,
			RedirectAttributes redAtt, HttpSession session) 
	{
		if(mServ.authenticateUser(username, passwordEntry)) {
			session.setAttribute("userLoggedIn", mServ.findUserByUsername(username));
			return "redirect:/dashboard";
		} else {
			redAtt.addFlashAttribute("error", "Sorry, username/password combination not found");
			return "login.jsp";
		}		
	}
	
	@GetMapping("/register")
	public String registerPage(@ModelAttribute("newUser") User user) {
		return "registration.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User user, BindingResult result, HttpSession session) {
		System.out.println("Made it to post");
		uVal.validate(user, result);
		if(result.hasErrors()) {
			System.out.println("There are errors");
			return "registration.jsp";
		} else {
			System.out.println("Going to save...");
			mServ.saveUser(user);
			System.out.println("Should have saved...");
			session.setAttribute("userLoggedIn", user);
			return "redirect:/dashboard";			
		}
	}
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model viewModel) {
		if(session.getAttribute("userLoggedIn") == null) {
			return "redirect:/login";
		} else {
			User user = (User)session.getAttribute("userLoggedIn");
			viewModel.addAttribute("user", user);
			return "dashboard.jsp";
		}
	}
	
	
	

}
