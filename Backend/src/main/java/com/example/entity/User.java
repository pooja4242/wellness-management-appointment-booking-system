package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User{
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generate ID
	    private Long id;
	
	@Column(name = "firstname", nullable = false)
    private String firstname;
	
	@Column(name = "lastname", nullable = false)
    private String lastname;
	
	@Column(name = "username", nullable = false)
    private String username;

	
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "password", nullable = false)
    private String password;

    // Default constructor
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Parameterized constructor for initializing a User object with all fields.
	public User(Long userid,String firstname,String lastname,String username, String email, String mobileNumber, String password) {
		super();
		this.id = userid;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
	}

	// Getters and setters for accessing and modifying the fields.
	public Long getUserid() {
		return id;
	}

	public void setUserid(Long userid) {
		this.id = userid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}