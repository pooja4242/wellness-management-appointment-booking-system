import React, { useState } from 'react'; // Import React and useState hook
import axios from 'axios'; // Import axios for HTTP requests
import { useNavigate } from 'react-router-dom'; // Import useNavigate hook for navigation
import './login.css'; // Import CSS specific to Login component

/**
 * Login Component
 * Provides a login form for users to authenticate with email and password.
 */
const Login = () => {
  // State to manage form inputs
  const [email, setEmail] = useState(''); // State for email input
  const [password, setPassword] = useState(''); // State for password input
  const [errors, setErrors] = useState({}); // State to manage form validation errors
  const navigate = useNavigate(); // Hook for navigation

  /**
   * Validate the form inputs.
   * Checks if email and password fields are filled in.
   * @returns {boolean} - Returns true if the form is valid, false otherwise.
   */
  const validateForm = () => {
    const errors = {};
    if (!email) errors.email = 'Email is required'; // Check if email is empty
    if (!password) errors.password = 'Password is required'; // Check if password is empty
    
    setErrors(errors); // Update errors state
    return Object.keys(errors).length === 0; // Return true if no errors
  };

  /**
   * Handle form submission.
   * Sends a POST request to the server with email and password.
   * If successful, stores email in localStorage and navigates to the appointment page.
   * @param {Object} e - Event object from the form submission.
   */
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent default form submission behavior
    if (validateForm()) { // Validate form before submitting
      try {
        const response = await axios.post('http://localhost:9099/api/users/login', { email, password }); // Send login request
        
        if (response.data === "Login Successful") { // Check if login was successful
          localStorage.setItem('email', email); // Store email in localStorage
          navigate('/appointment'); // Navigate to the appointment page
        }
      } catch (error) {
        console.error('Login error:', error); // Log errors if the request fails
        alert('Login failed. Please check your credentials and try again.'); // Show error message
      }
    }
  };

  return (
    <>
      <div className="container">
        <div className="test">
          {/* This div can be used to add any additional content for the wellness section */}
        </div>
        <div className="form">
          <form onSubmit={handleSubmit}>
            <div>
              <h2>Login</h2>
              <label>Email:</label>
              <input
                type="text"
                value={email}
                onChange={(e) => setEmail(e.target.value)} // Update email state on input change
                required
              />
              {errors.email && <p className="error">{errors.email}</p>} {/* Display email validation error */}
            </div>
            <div>
              <label>Password:</label>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)} // Update password state on input change
                required
              />
              {errors.password && <p className="error">{errors.password}</p>} {/* Display password validation error */}
            </div>
            <button type="submit">Login</button> {/* Submit button for login form */}
            <p>
              Don't have an account? <a href="/register">Register</a> {/* Link to registration page */}
            </p>
            <p>
              <a href="/">Back to home </a> {/* Link to home page */}
            </p>
          </form>
        </div>
      </div>
    </>
  );
};

export default Login;
