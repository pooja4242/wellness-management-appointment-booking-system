import React, { useEffect, useState } from 'react'; // Import React and hooks for managing state and lifecycle methods
import axios from 'axios'; // Import axios for making HTTP requests
import './AppointmentForm.css'; // Import CSS file specific to the AppointmentForm component
import { useNavigate } from 'react-router-dom'; // Import useNavigate hook for programmatic navigation

/**
 * AppointmentForm Component
 * Allows users to book appointments by selecting a service, date, time, and doctor.
 */
const AppointmentForm = () => {
    // State to manage form data
    const [formData, setFormData] = useState({
        service: '',
        date: '',
        time: '',
        doctor: '',
    });
    // State to manage user data fetched from the API
    const [user, setUser] = useState({});
    const navigate = useNavigate(); // Hook for navigation

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
    
    /**
     * useEffect to fetch user details when the component mounts.
     * Retrieves user data based on email stored in localStorage.
     */
    useEffect(() => {
        const userEmail = localStorage.getItem('email'); // Retrieve user's email from localStorage
        axios.get(`http://localhost:9099/api/users/${userEmail}`)
            .then(data => {
                console.log(data?.data); // Log the fetched user data
                setUser(data?.data); // Update the user state with fetched data
            })
            .catch(error => {
                console.error('Error fetching user data:', error); // Log errors if the request fails
            });
    }, []); // Empty dependency array ensures this effect runs only once on component mount

    const today = new Date().toISOString().split('T')[0]; // Get today's date in 'YYYY-MM-DD' format

    /**
     * Handle changes to form inputs.
     * Updates the formData state with the new value from the input field.
     * @param {Object} e - Event object from the input field
     */
    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value }); // Update the corresponding form field value in the state
    };

    /**
     * Handle form submission.
     * Sends a POST request to book an appointment with the data provided in the form.
     * @param {Object} e - Event object from the form submission
     */
    const handleSubmit = async (e) => {
        e.preventDefault(); // Prevent default form submission behavior
        try {
            // Prepare data to send with userId included
            const dataToSend = { ...formData, time: formData.time + ":00", user: { userid: user.userid } };
            console.log(dataToSend); // Log the data to be sent
            const response = await axios.post('http://localhost:9099/api/appointment/bookAppointment', dataToSend);
            alert('Appointment booked successfully!'); // Notify user of successful booking

            // Clear form fields after successful submission
            setFormData({
                service: '',
                date: '',
                time: '',
                doctor: '',
            });
            // Optionally, navigate to the booking list page or another page
            // navigate('appointment/BookingList');
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
                {/* Header with logout button */}
                <header className="logout-container">
                    <button onClick={handleLogout} className="logout-btn">Logout</button>
                </header>
                {/* Display the logged-in user's name and ID */}
                <h2>Hi {user.firstname} !</h2>
                {/* <h3>Your User id is {user.userid}</h3> */}
                {/* Form heading */}
                <br></br>
                <h1>Book an Appointment</h1>
                {/* Appointment booking form */}
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

                    <button type="submit" className="submit-btn">Submit</button> {/* Submit button */}
                </form>
                <p><a href="/BookingList">View your Bookings</a></p> {/* Link to view bookings */}
            </div>
        </div>
    );
};

export default AppointmentForm;
