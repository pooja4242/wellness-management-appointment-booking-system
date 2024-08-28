package com.example.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	

	List<Appointment> findByDate(Date date);    //Finds all appointments scheduled on a specific date.
	List<Appointment> findByUserId(Long userid);  //Finds all appointments associated with a specific user ID.

}
