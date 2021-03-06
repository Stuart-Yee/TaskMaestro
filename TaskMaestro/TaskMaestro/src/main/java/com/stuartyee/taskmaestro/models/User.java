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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
	private String name;
	
	@Size(min=5, max=20, message="Username must be between 5 and 20 characters long")
	@NotBlank
	private String username;
	
	@Size(min=8, message="Password must be at least 8 characters long") //Introduce @Pattern regex to enforce better password complexity
	@NotBlank
	private String password;
	
	@Transient
	private String passwordConfirmation;
	
	private int permissions; //Permissions levels coded as an integer rather than string to avoid errors
	
	////
	
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Task> createdTasks;
	
	@OneToMany(mappedBy="owner", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Task> ownedTasks;
	/////
	
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


	public User() {
	}  
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public int getPermissions() {
		return permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}

	public List<Task> getCreatedTasks() {
		return createdTasks;
	}

	public void setCreatedTasks(List<Task> createdTasks) {
		this.createdTasks = createdTasks;
	}

	public List<Task> getOwnedTasks() {
		return ownedTasks;
	}

	public void setOwnedTasks(List<Task> ownedTasks) {
		this.ownedTasks = ownedTasks;
	}

	public List<Task> getHelpingTasks() {
		return helpingTasks;
	}

	public void setHelpingTasks(List<Task> helpingTasks) {
		this.helpingTasks = helpingTasks;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Comment> getLikedComments() {
		return this.likedComments;
	}

	public void setLikedComments(List<Comment> likedComments) {
		this.likedComments = likedComments;
	}
	
	
	
	
	

}
