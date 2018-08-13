package com.vnpt.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user_attempts")
public class UserAttempts {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
	@SequenceGenerator(sequenceName = "emp_seq", allocationSize = 1, name = "emp_seq")
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "username", unique = true, nullable = false, length = 60)
	private String username;

	@Column(name = "attempts", unique = true, nullable = false, length = 60)
	private int attempts;

	@Column(name = "lastModified")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;

	public UserAttempts() {

	}

	public UserAttempts(String username, int attempts, Date lastModified) {
		super();
		this.username = username;
		this.attempts = attempts;
		this.lastModified = lastModified;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}
