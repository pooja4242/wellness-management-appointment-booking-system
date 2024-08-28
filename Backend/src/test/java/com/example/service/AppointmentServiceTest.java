package com.example.service;

import com.example.entity.Appointment;
import com.example.entity.User;
import com.example.repository.AppointmentRepository;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the AppointmentService class.
 * It uses Mockito to mock dependencies and test the service methods in isolation.
 */
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the saveAppointment method when the user is not found.
     * Verifies that a RuntimeException is thrown when the user does not exist.
     */
    @Test
    void saveAppointment() {
        User user = new User();
        user.setUserid(1L);

        Appointment appointment = new Appointment();
        appointment.setUser(user);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment savedAppointment = appointmentService.saveAppointment(appointment);

        assertNotNull(savedAppointment);
        assertEquals(user, savedAppointment.getUser());
        verify(userRepository, times(1)).findById(1L);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    /**
     * Tests the saveAppointment method when the user is not found.
     * Verifies that a RuntimeException is thrown when the user does not exist.
     */
    @Test
    void saveAppointment_UserNotFound() {
        Appointment appointment = new Appointment();
        User user = new User();
        user.setUserid(1L);
        appointment.setUser(user);

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.saveAppointment(appointment);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(anyLong());
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    /**
     * Tests the findByDate method of AppointmentService.
     * Verifies that appointments are retrieved correctly by date.
     */
    @Test
    void findByDate() {
        Date date = new Date(System.currentTimeMillis());
        List<Appointment> appointments = List.of(new Appointment());

        when(appointmentRepository.findByDate(date)).thenReturn(appointments);

        List<Appointment> result = appointmentService.findByDate(date);

        assertNotNull(result);
        assertEquals(appointments.size(), result.size());
        verify(appointmentRepository, times(1)).findByDate(date);
    }

    /**
     * Tests the deleteAppointment method of AppointmentService.
     * Verifies that the appointment is deleted correctly when it exists.
     */
    @Test
    void deleteAppointment() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        appointmentService.deleteAppointment(appointmentId);

        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, times(1)).deleteById(appointmentId);
    }

    /**
     * Tests the deleteAppointment method when the appointment is not found.
     * Verifies that a RuntimeException is thrown when the appointment does not exist.
     */
    @Test
    void deleteAppointment_NotFound() {
        Long appointmentId = 1L;
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.deleteAppointment(appointmentId);
        });

        assertEquals("No Appointment", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, never()).deleteById(appointmentId);
    }

    /**
     * Tests the updateAppointment method of AppointmentService.
     * Verifies that the appointment is updated correctly when both the appointment and user exist.
     */
    @Test
    void updateAppointment() {
        Long appointmentId = 1L;
        User user = new User();
        user.setUserid(1L);
        
        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setAppointmentId(appointmentId);
        updatedAppointment.setUser(user);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(new Appointment()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(updatedAppointment);

        Appointment result = appointmentService.updateAppointment(appointmentId, updatedAppointment);

        assertNotNull(result);
        assertEquals(appointmentId, result.getAppointmentId());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(userRepository, times(1)).findById(1L);
        verify(appointmentRepository, times(1)).save(updatedAppointment);
    }

    /**
     * Tests the updateAppointment method when the user is not found.
     * Verifies that a RuntimeException is thrown when the user does not exist.
     */
    @Test
    void updateAppointment_UserNotFound() {
    	 Long userId = 1L;
    	 Long appointmentId = 2L;
    	 
    	 User user = new User();
    	    user.setUserid(userId);
    	    
        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setAppointmentId(appointmentId);
        updatedAppointment.setUser(user);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(new Appointment()));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.updateAppointment(appointmentId, updatedAppointment);
        });

        assertEquals("User not found", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(userRepository, times(1)).findById(anyLong());
        verify(appointmentRepository, never()).save(updatedAppointment);
    }

    /**
     * Tests the getAppointmentsByUserId method of AppointmentService.
     * Verifies that appointments are retrieved correctly by user ID.
     */
    @Test
    void getAppointmentsByUserId() {
        Long userId = 1L;
        List<Appointment> appointments = List.of(new Appointment());

        when(appointmentRepository.findByUserId(userId)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByUserId(userId);

        assertNotNull(result);
        assertEquals(appointments.size(), result.size());
        verify(appointmentRepository, times(1)).findByUserId(userId);
    }
    
    /**
     * Tests the existsById method of AppointmentService.
     * Verifies that the method correctly checks the existence of an appointment by ID.
     */

    @Test
    void existsById() {
        Long appointmentId = 1L;
        when(appointmentRepository.existsById(appointmentId)).thenReturn(true);

        boolean exists = appointmentService.existsById(appointmentId);

        assertTrue(exists);
        verify(appointmentRepository, times(1)).existsById(appointmentId);
    }
}
