package com.stuartyee.taskmaestro.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stuartyee.taskmaestro.models.Task;
import com.stuartyee.taskmaestro.models.User;
import com.stuartyee.taskmaestro.services.MainService;
import com.stuartyee.taskmaestro.validators.UserValidator;

@Controller
public class MainController {
	
	@Autowired
	MainService mServ;
	@Autowired 
	UserValidator uVal;
	
	@RequestMapping("/")
	public String defaultNav() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login.jsp";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String passwordEntry,
			RedirectAttributes redAtt, HttpSession session) 
	{
		System.out.println(username);
		System.out.println(passwordEntry);
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
			return "registration.jsp";
		} else {
			mServ.saveUser(user);
			session.setAttribute("userLoggedIn", user);
			return "redirect:/dashboard";			
		}
	}
	
	//Get and Post pair for editing or creating a Task and then saving it
	@GetMapping("/tasks/new")
	public String editTask(HttpSession session, Model viewModel) {
		if(session.getAttribute("userLoggedIn") == null) {
			return "redirect:/login";
		} else {
			User user = (User)session.getAttribute("userLoggedIn");
			viewModel.addAttribute("user", user);
			viewModel.addAttribute("users", mServ.findAllUsers());
			return "editTask.jsp";
		}		
	}
	
	@PostMapping("/tasks/new")
	public String saveTask(HttpSession session,
			@RequestParam("description") String description,
			@RequestParam("dueDate") String dueDate,
			@RequestParam("owner") Long id) throws ParseException {
		Task newTask = new Task();
		User loggedIn = (User) session.getAttribute("userLoggedIn");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(dueDate);
		
		//Move this logic to the service
		newTask.setCreator(loggedIn);
		newTask.setOwner(mServ.findUserbyId(id));
		newTask.setDescription(description);
		newTask.setDueDate(date);
		newTask.setCompleted(false);
		mServ.saveTask(newTask);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model viewModel) {
		if(session.getAttribute("userLoggedIn") == null) {
			return "redirect:/login";
		} else {
			User user = (User)session.getAttribute("userLoggedIn");
			viewModel.addAttribute("user", user);
			viewModel.addAttribute("openTasks", mServ.findOpenTasksAsc()); 
			return "dashboard.jsp";
		}
	}
	
	@GetMapping("/tasks/{id}/view")
	public String showTask(HttpSession session, Model viewModel, @PathVariable("id") Long id) {
		if(session.getAttribute("userLoggedIn") == null) {
			return "redirect:/login";
		} else {
			Task task = mServ.findTaskById(id);
			User user = (User)session.getAttribute("userLoggedIn");
			List<User> helpers = task.getHelpers();
			viewModel.addAttribute("user", user);
			viewModel.addAttribute("Task", task);
			viewModel.addAttribute("comments", mServ.findCommentsByTask(task));
			viewModel.addAttribute("helpers", helpers);
			viewModel.addAttribute("notHelpers", mServ.findNotHelping(task));
			Date date = task.getDueDate();
			String formattedDate = mServ.convertDate(date);
			viewModel.addAttribute("dueDate", formattedDate);
			
			return "showTask.jsp";
		}
	}
	
	@PostMapping("/tasks/{id}/addHelper")
	public String addHelper(
			@PathVariable("id") Long id, 
			@RequestParam("newHelper") User newHelper
			) {
		Task task = mServ.findTaskById(id);
		task.getHelpers().add(newHelper);
		mServ.saveTask(task);
		return "redirect:/tasks/{id}/view";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	//Comment
	@PostMapping("/tasks/{id}/comment")
	public String postComment(HttpSession session, @RequestParam("comment") String comment, @PathVariable("id") Long id) {
		if(session.getAttribute("userLoggedIn") == null) {
			return "redirect:/login";
		} else {
			User user = (User)session.getAttribute("userLoggedIn");
			Task task = mServ.findTaskById(id);
			mServ.saveComment(comment, user, task);
			return "redirect:/tasks/{id}/view";
		}
	}
	
	@RequestMapping("/tasks/{task_id}/delete/{comment_id}")
	public String deleteComment(
			HttpSession session,
			@PathVariable("task_id") Long task_id,
			@PathVariable("comment_id") Long comment_id
			) {
		if(session.getAttribute("userLoggedIn") == null) {
			return "redirect:/login";
		} else {
			mServ.deleteComment(mServ.findCommentById(comment_id), (User)session.getAttribute("userLoggedIn"));
			return "redirect:/tasks/{task_id}/view";
		}
		
	}
	
	
	
	

}
