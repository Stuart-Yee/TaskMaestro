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
	
	@Column(updatable=false)
	@ManyToOne(fetch = FetchType.LAZY)
	private User creator;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User owner;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "helpersandtasks",
			joinColumns = @JoinColumn(name = "task_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
			)
	private List<Task> helpers;
	
	@OneToMany(mappedBy="parentTask", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Comment> comments;
	

}
