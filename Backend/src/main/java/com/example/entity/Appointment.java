package com.example.entity;



import java.sql.Time;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="appointment")
public class Appointment {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generate ID
	    private Long appointmentId;
	
	@Column(name = "service" )
	private String service;
	
	@Column(name = "date", nullable = false)
	private Date date;
	
	@Column(name = "time", nullable = false)
	private Time time;
	
	@Column(name = "doctor", nullable = false)
	private String doctor;
	
	// Defines a many-to-one relationship between appointments and users; each appointment belongs to a user
	// Joins the `userid` column in the appointment table to the primary key `id` of the user entity
	
	@ManyToOne
	@JoinColumn(name = "userid", nullable = false, referencedColumnName = "id")
	private User user;
	
	// Default constructor
	public Appointment() {
		super();
		
	}
    
	// Parameterized constructor for initializing a User object with all fields.
	public Appointment(Long appointmentId, String service, Date date, Time time, String doctor,User user) {
		super();
		this.appointmentId = appointmentId;
		this.service = service;
		this.date = date;
		this.time = time;
		this.doctor = doctor;
		this.user = user;
	}

	// Getters and setters for accessing and modifying the fields.
	
	public Long getAppointmentId() {
		return appointmentId;
	}

	 
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	 
	public String getService() {
		return service;
	}


	public void setService(String service) {
		this.service = service;
	}

	
	public Date getDate() {
		return date;
	}

	
	public void setDate(Date date) {
		this.date = date;
	}

	
	public Time getTime() {
		return time;
	}

	
	public void setTime(Time time) {
		this.time = time;
	}

	
	public String getDoctor() {
		return doctor;
	}

	
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	 
	public User getUser() {
		return user;
	}

	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	

}
