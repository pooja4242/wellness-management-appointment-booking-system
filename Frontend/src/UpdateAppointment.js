import React, { useState, useEffect } from 'react'; // Import React and necessary hooks
import axios from 'axios'; // Import axios for HTTP requests
import { useNavigate, useParams, useLocation } from 'react-router-dom'; // Import hooks for routing and navigation
import './UpdateAppointment.css'; // Import CSS specific to UpdateAppointment component

/**
 * UpdateAppointment Component
 * Allows users to update an existing appointment by changing the service, date, time, and doctor.
 */
const UpdateAppointment = () => {
    // State to manage form data
    const [formData, setFormData] = useState({
        service: '',
        date: '',
        time: '',
        doctor: '',
    });

    // State to manage user data
    const [user, setUser] = useState({});
    const { id } = useParams(); // Get appointment ID from the URL params
    const navigate = useNavigate(); // Hook for navigation
    const location = useLocation(); // Hook to get current location and state

    // Array of services offered
    const service = [
        "Yoga Sessions", 
        "Mental Health Counselling", 
        "Speech Therapy", 
        "Orthopedic Therapy",
        "Nutrition Counselling"
    ];

    // Array of doctors available for appointments
    const doctors = [
        "Dr. Priya Rajput",
        "Dr. Priyanka Jha", 
        "Dr. Pooja C", 
        "Dr. Mriganka Shekhar Bhunia", 
        "Dr. Prince Kumar",
        "Dr. Venkatesh Medikonda"
    ];

    // Array to store time slots generated
    const times = [];
    // Generate time slots from 9:00 AM to 5:00 PM at 1-hour intervals
    for (let hour = 9; hour <= 17; hour++) {
        const formattedHour = hour.toString().padStart(2, '0');
        const formattedMinute = '00'; // Always set minutes to '00'
        const time = `${formattedHour}:${formattedMinute}`;
        times.push(time);
    }

    // Format time from the location state
    const time = location.state.time; // e.g., "10:00:00"
    const [hours, minutes] = time.split(':');
    const formattedTime = `${hours}:${minutes}`; // Format to "HH:MM"

    /**
     * useEffect to fetch user details and pre-fill form with appointment data
     * Runs when the component mounts and when location.state changes
     */
    useEffect(() => {
        const userEmail = localStorage.getItem('email'); // Retrieve user's email from localStorage
        axios.get(`http://localhost:9099/api/users/${userEmail}`)
            .then(data => {
                console.log(data?.data); // Log fetched user data
                setUser(data?.data); // Update user state with fetched data
            })
            .catch(error => {
                console.error('Error fetching user data:', error); // Log error if fetching fails
            });

        // Pre-fill form data with values passed from the previous component via location.state
        if (location.state) {
            console.log('Location state:', location.state); // Log the state passed via location
            setFormData({
                service: location.state.service,
                date: location.state.date,
                time: formattedTime,
                doctor: location.state.doctor,
            });
        }
    }, [location.state]); // Dependencies to re-run effect

    const today = new Date().toISOString().split('T')[0]; // Get today's date in 'YYYY-MM-DD' format

    /**
     * Handle changes to form inputs.
     * Updates the formData state with the new value from the input field.
     * @param {Object} e - Event object from the input field
     */
    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value }); // Update formData state with the new value
    };

    /**
     * Handle form submission.
     * Sends a PUT request to update an existing appointment with the data provided in the form.
     * @param {Object} e - Event object from the form submission
     */
    const handleSubmit = async (e) => {
        e.preventDefault(); // Prevent default form submission behavior
        try {
            // Prepare data to send with userId included
            const dataToSend = { ...formData, time: formData.time + ":00", user: { userid: user.userid } };
            console.log('Submitting form with data:', dataToSend); // Log the data to be sent
            await axios.put(`http://localhost:9099/api/appointment/update/${id}`, dataToSend);
            alert('Appointment updated successfully!'); // Notify user of successful update
        } catch (error) {
            console.error('Error submitting the form:', error); // Log errors if the request fails
        }
    };

    /**
     * Handle user logout.
     * Clears user email from localStorage and navigates to the home page.
     */
    const handleLogout = () => {
        localStorage.removeItem('email'); // Clear user email from local storage
        navigate('/'); // Navigate to the home page
    };

    return (
        <div>
            <div className="appointment-form-container">
                <div className="logout-container">
                    <button onClick={handleLogout} className="logout-btn">Logout</button>
                </div>
                <h1>Update Your Appointment</h1>
                <form onSubmit={handleSubmit} className="appointment-form">
                    <div className="form-group">
                        <label htmlFor="services">Select Service</label>
                        <select
                            id="service"
                            name="service"
                            value={formData.service}
                            onChange={handleChange}
                            required
                        >
                            <option value="" disabled>Select Service</option>
                            {service.map((service, index) => (
                                <option key={index} value={service}>{service}</option>
                            ))}
                        </select>
                    </div>

                    <div className="form-group">
                        <label htmlFor="date">Preferred Date</label>
                        <input
                            type="date"
                            id="date"
                            name="date"
                            value={formData.date}
                            onChange={handleChange}
                            required
                            min={today} // Restrict date selection to today or later
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="time">Preferred Time</label>
                        <select
                            id="time"
                            name="time"
                            value={formData.time}
                            onChange={handleChange}
                            required
                        >
                            <option value="" disabled>Select Time</option>
                            {times.map((time, index) => (
                                <option key={index} value={time}>{time}</option>
                            ))}
                        </select>
                    </div>

                    <div className="form-group">
                        <label htmlFor="doctor">Select Doctor</label>
                        <select
                            id="doctor"
                            name="doctor"
                            value={formData.doctor}
                            onChange={handleChange}
                            required
                        >
                            <option value="" disabled>Select Doctor</option>
                            {doctors.map((doctor, index) => (
                                <option key={index} value={doctor}>{doctor}</option>
                            ))}
                        </select>
                    </div>

                    <button type="submit" className="submit-btn">Update</button> {/* Update button */}
                </form>
                <p><a href="/BookingList">View your Bookings</a></p> {/* Link to view bookings */}
            </div>
        </div>
    );
};

export default UpdateAppointment;
