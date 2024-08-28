package com.example.controller;

import java.sql.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Appointment;

import com.example.service.AppointmentService;
import com.example.service.UserService;

@RestController
@RequestMapping("/api/appointment")
@CrossOrigin(origins="http://localhost:3000") // Allows cross-origin requests from localhost:3000 (your frontend)
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private UserService userService;
	
	// endpoint to verify the server is running
	@GetMapping
    public String healthCheck() {
        return "Server is up and running!";
    }
	
	
    //Create 
	@PostMapping("/bookAppointment")
	public ResponseEntity<Appointment> bookAppointment(@RequestBody Appointment appointment) {
	    if (appointment.getAppointmentId() != null && appointmentService.existsById(appointment.getAppointmentId())) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Conflict if the ID already exists
	    }
	    Appointment savedAppointment = appointmentService.saveAppointment(appointment);
	    return ResponseEntity.ok(savedAppointment);
	}

	
	//get appointments by date
	 @GetMapping("/date/{date}")
	    public List<Appointment> getUserByDate(@PathVariable Date date) {
		 return appointmentService.findByDate(date);
		
	    }
	 //get all appointments by user id
	 @GetMapping("/user/{userid}")
	    public ResponseEntity<List<Appointment>> getAppointmentsByUserId(@PathVariable Long userid) {
	        List<Appointment> appointments = appointmentService.getAppointmentsByUserId(userid);
	        if (appointments.isEmpty()) {
	            return ResponseEntity.noContent().build(); 
	        }
	        return ResponseEntity.ok(appointments); 
	    }
	 
	 
	 //get all the appointments
	 @GetMapping("/all")
	    public List<Appointment> getAllAppointments() {
	        return appointmentService.findAllAppointments();
	    }
	 
	 
	 //update the appointment by id
	 @PutMapping("/update/{id}")
	    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment) {
		 
	        return appointmentService.updateAppointment(id, updatedAppointment);
		 
		}
		 
	 
	 //Delete the appointment by id
	 @DeleteMapping("/delete/{id}")
	    public String deleteAppointment(@PathVariable Long id) {
		 if(id!=null) {
	        appointmentService.deleteAppointment(id);
		 }
	        return "Appointment with ID " + id + " deleted successfully.";
	 }
	

}
