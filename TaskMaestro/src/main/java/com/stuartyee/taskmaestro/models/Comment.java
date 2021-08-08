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
@Table(name="comments")
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable=false)
	private Date createdAt;
	
	private Date updatedAt;
	
	@NotBlank
	@Size(max=500, message="Must be less than 500 characters")
	private String content;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="task_id", updatable=false)
	private Task parentTask;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", updatable=false)
	private User commenter;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "likes",
			joinColumns = @JoinColumn(name = "comment_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
			)
	private List<User> likers;  
	
	@OneToMany(mappedBy="parentComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Comment> replies;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="comment_id", updatable=false)
	private Comment parentComment;
	
	private int numberOfLikes;

	public Comment() {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}

	public User getCommenter() {
		return commenter;
	}

	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}

	public List<User> getLikers() {
		return likers;
	}

	public void setLikers(List<User> likers) {
		this.likers = likers;
	}

	public List<Comment> getReplies() {
		return replies;
	}

	public void setReplies(List<Comment> replies) {
		this.replies = replies;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}
	
	
	
	

}
