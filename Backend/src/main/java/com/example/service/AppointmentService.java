package com.example.service;

import java.sql.Date;
import java.util.List;

import com.example.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Appointment;
import com.example.entity.User;
import com.example.repository.AppointmentRepository;
import com.example.repository.UserRepository;

@Service
public class AppointmentService {
	
	@Autowired
	private UserRepository userRepository;

	
	@Autowired
	private AppointmentRepository appointmentrepo;
	
	public Appointment saveAppointment(Appointment appointment) {
	    // Fetch the user from the database
	    User user = userRepository.findById(appointment.getUser().getUserid())
	                              .orElseThrow(() -> new ResourceNotFoundException("User not found"));

	    // Attach the user to the appointment
	    appointment.setUser(user);

	    // Save the appointment
	    return appointmentrepo.save(appointment);
	}
	
	//Find all appointments by a specific date.
	public List<Appointment> findByDate(Date date){
		return appointmentrepo.findByDate(date);
	}
	
    //Retrieve all appointments.
	public List<Appointment> findAllAppointments(){
		return appointmentrepo.findAll();
	}
	
	
	//Delete an appointment by its ID.
	public void deleteAppointment(Long id) {
		 Appointment existingAppointment = appointmentrepo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("No Appointment"));

		appointmentrepo.deleteById(id);
		}
	
	//Update an existing appointment.
	 public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
	        // Check if the appointment exists
	        Appointment existingAppointment = appointmentrepo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

	        // Ensure the user exists
	        User user = userRepository.findById(updatedAppointment.getUser().getUserid())
	                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
	        updatedAppointment.setUser(user);

	        // Update and save the appointment
	        updatedAppointment.setAppointmentId(id);
	        return appointmentrepo.save(updatedAppointment);
	    }
	 
// Retrieve all appointments for a specific user.
	 public List<Appointment> getAppointmentsByUserId(Long userid) {
	        return appointmentrepo.findByUserId(userid);
	    }
	 
	 //Check if an appointment exists by its ID.
	public boolean existsById(Long id) {
        return appointmentrepo.existsById(id);
    }
	
	

}
