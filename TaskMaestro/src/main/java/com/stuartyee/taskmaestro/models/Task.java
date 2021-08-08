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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable=false)
	private Date createdAt;
	
	private Date updatedAt;
	
	@Size(max=1000, message="Description must be at least 1000 characters")
	@NotBlank
	private String description;
	
	private Date dueDate;
	
	private Boolean completed;
	
	private Date completedOn;
	
	
	///
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="creator_user_id", updatable=false)
	private User creator;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="owner_user_id")
	private User owner;
	///
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "helpersandtasks",
			joinColumns = @JoinColumn(name = "task_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
			)
	private List<Task> helpers;
	
	@OneToMany(mappedBy="parentTask", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Comment> comments;

	public Task() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public Date getCompletedOn() {
		return completedOn;
	}

	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<Task> getHelpers() {
		return helpers;
	}

	public void setHelpers(List<Task> helpers) {
		this.helpers = helpers;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
	
	
	

}
