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
	
	@Column(updatable=false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Task parentTask;
	
	@Column(updatable=false)
	@ManyToOne(fetch = FetchType.LAZY)
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
	private Comment parentComment;
	
	

}
