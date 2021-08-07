package com.stuartyee.taskmaestro.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable=false)
	private Date createdAt;
	
	private Date updatedAt;
	
	@NotBlank
	private String naame;
	
	@Size(min=5, max=20, message="Username must be between 5 and 20 characters long")
	@NotBlank
	private String username;
	
	@Size(min=8, message="Password must be at least 8 characters long") //Introduct @Pattern regex to enforce better password complexity
	@NotBlank
	private String password;
	
	@Transient
	private String passwordConfirmation;
	
	private int permissions; //Permissions levels coded as an integer rather than string to avoid errors
	
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Task> createdTasks;
	
	@OneToMany(mappedBy="owner", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Task> ownedTasks;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "helpersandtasks",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "task_id")
			)
	private List<Task> helpingTasks;
	
	@OneToMany(mappedBy="commenter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "likes",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "comment_id")
			)
	private List<Comment> likedComments;  
	
	

}
