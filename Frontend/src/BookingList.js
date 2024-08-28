import React, { useState, useEffect } from 'react'; // Import React and hooks
import axios from 'axios'; // Import axios for API requests
import './BookingList.css'; // Import specific CSS file for BookingList
import { useNavigate } from 'react-router-dom'; // Import useNavigate hook for navigation

/**
 * BookingList Component
 * Displays a list of bookings for the logged-in user.
 */
const BookingList = () => {
    const [bookings, setBookings] = useState([]); // State for storing bookings

    const navigate = useNavigate(); // Hook for navigation
    
    /**
     * useEffect to fetch user details and bookings when the component mounts.
     */
    useEffect(() => {
        const userEmail = localStorage.getItem('email'); // Retrieve the user's email from localStorage
        
        // Fetch user details based on email
        axios.get(`http://localhost:9099/api/users/${userEmail}`)
            .then(response => {
                const fetchedUser = response?.data; // Extract user data from the response

                const userId = fetchedUser?.userid; // Get the user's ID
                if (userId) {
                    fetchBookingsByUserId(userId); // Fetch bookings for the user
                }
            })
            .catch(error => {
                console.error('Error fetching user data:', error); // Log errors
            });
    }, []); // Empty dependency array to run only once on component mount

    /**
     * Fetch bookings for a given user ID.
     * @param {string} userId - The ID of the user.
     */
    const fetchBookingsByUserId = (userId) => {
        axios.get(`http://localhost:9099/api/appointment/user/${userId}`)
            .then(response => {
                console.log(response?.data); // Log the fetched bookings data
                setBookings(response?.data); // Update the bookings state with fetched data
            })
            .catch(error => {
                console.error('Error fetching bookings:', error); // Log errors
            });
    };

    /**
     * Handle the deletion of a booking.
     * @param {string} appointmentId - The ID of the booking to delete.
     */
    const handleDelete = async (appointmentId) => {
        try {
            await axios.delete(`http://localhost:9099/api/appointment/delete/${appointmentId}`);
            // Remove the deleted booking from the state
            setBookings(bookings.filter(booking => booking.appointmentId !== appointmentId));
            alert('Booking deleted successfully!');
            navigate(`/BookingList`); // Refresh the booking list
        } catch (error) {
            console.error('Error deleting booking:', error); // Log errors
        }
    };

    /**
     * Handle navigation back to the appointment page.
     */
    const handleLogout = () => {
        navigate('/appointment'); // Navigate to the appointment page
    };

    /**
     * Navigate to the edit form with the current booking data.
     * @param {Object} booking - The booking data to edit.
     */
    const handleEdit = (booking) => {
        navigate(`/update/${booking.appointmentId}`, { state: booking }); // Navigate to the update page with booking data
    };

    return (
        <div className="booking-list-container">
            <h1>Booking List</h1>
            <table className="booking-table">
                <thead>
                    <tr>
                        <th>Appointment ID</th>
                        <th>Service</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Doctor</th>
                        <th>User Name</th>
                        <th>User Email</th>
                        <th>Actions</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {bookings.map(booking => (
                        <tr key={booking.appointmentId}>
                            <td>{booking.appointmentId}</td>
                            <td>{booking.service}</td>
                            <td>{booking.date}</td>
                            <td>{booking.time}</td>
                            <td>{booking.doctor}</td>
                            <td>{booking.user.firstname} {booking.user.lastname}</td>
                            <td>{booking.user.email}</td>
                            <td>
                                <button onClick={() => handleEdit(booking)}>Edit</button>
                            </td>
                            <td>  
                                <button onClick={() => handleDelete(booking.appointmentId)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className="logout-container">
                <button onClick={handleLogout} className="logout-btn">Back</button>
            </div>
        </div>
    );
};

export default BookingList;
