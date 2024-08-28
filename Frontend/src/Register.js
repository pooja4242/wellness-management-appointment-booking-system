import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './register.css'; // Import the specific CSS file for register

const Register = () => {
  // Define state variables for form fields
  const [firstname, setFirstName] = useState('');
  const [lastname, setLastName] = useState(''); 
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [mobileNumber, setMobileNumber] = useState(''); 
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [errors, setErrors] = useState({}); // State variable for form validation errors
  const navigate = useNavigate(); // Hook to programmatically navigate to different routes

  // Function to validate the form fields
  const validateForm = () => {
    const errors = {};
    
    // Validate first name
    if (!firstname) errors.firstname = 'First name is required'; 
    
    // Validate last name
    if (!lastname) errors.lastname = 'Last name is required'; 
    
    // Validate mobile number
    if (!mobileNumber) { 
      errors.mobileNumber = 'Mobile number is required'; 
    } else if (!/^\d+$/.test(mobileNumber)) { 
      errors.mobileNumber = 'Mobile number must contain only digits'; 
    } else if (mobileNumber.length !== 10) { 
      errors.mobileNumber = 'Mobile number must be exactly 10 digits'; 
    }
    
    // Validate username
    if (!username) errors.username = 'Username is required';
    
    // Validate email
    if (!email) {
      errors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      errors.email = 'Email is invalid';
    }
    
    // Validate password
    if (!password) errors.password = 'Password is required';
    else if (password.length < 6) errors.password = 'Password must be at least 6 characters';
    
    // Validate confirm password
    if (password !== confirmPassword) errors.confirmPassword = 'Passwords must match';

    setErrors(errors); // Set validation errors in state
    return Object.keys(errors).length === 0; // Return true if no errors
  };

  // Function to handle mobile number input change, allowing only digits
  const handleMobileChange = (e) => {
    const value = e.target.value;
    if (/^\d*$/.test(value)) { 
      setMobileNumber(value); 
    }
  };

  // Function to handle form submission
  const handleSubmit = (e) => {
    e.preventDefault(); // Prevent the default form submission behavior
    
    if (validateForm()) { // Validate the form before submitting
      console.log('Register:', { username, email, password, firstname, lastname, mobileNumber }); 
      
      // Make a POST request to the server to register the user
      fetch('http://localhost:9099/api/users/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, email, password, firstname, lastname, mobileNumber })
      })
      .then(response => response.json())
      .then(data => {
        console.log('Success:', data);
        alert("Registered Successfully ....Stay Happy and Healthy"); // Show success message
        navigate('/'); // Redirect to the home page
        
      })
      .catch(error => {
        console.error('Error:', error);
      });
     
    }
  };

  return (
    <div className="container register">
      <div className='demodiv'>
        {/* Additional content can be placed here if needed */}
      </div>
      
      <form onSubmit={handleSubmit}>
        <h2 style={{textAlign:"center"}}>REGISTER</h2>
        
        <div className="form-sections">
          <div className="form-section">
            <label>First Name:</label>
            <input
              type="text"
              value={firstname}
              onChange={(e) => setFirstName(e.target.value)}
              required
            />
            {errors.firstname && <p className="error">{errors.firstname}</p>} 

            <label>Last Name:</label>
            <input
              type="text"
              value={lastname}
              onChange={(e) => setLastName(e.target.value)}
              required
            />
            {errors.lastname && <p className="error">{errors.lastname}</p>} 

            <label>Mobile Number:</label>
            <input
              type="text"
              value={mobileNumber} 
              onChange={handleMobileChange}
              required
              maxLength="10"
            />
            {errors.mobileNumber && <p className="error">{errors.mobileNumber}</p>} 

            <label>Username:</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
            {errors.username && <p className="error">{errors.username}</p>}
          </div>
          
          <div className="form-section">
            <label>Email:</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
            {errors.email && <p className="error">{errors.email}</p>}

            <label>Password:</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            {errors.password && <p className="error">{errors.password}</p>}

            <label>Confirm Password:</label>
            <input
              type="password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
            />
            {errors.confirmPassword && <p className="error">{errors.confirmPassword}</p>}
          </div>
        </div>
        
        <button type="submit">Register</button>
        <p>
          Already have an account? <a href="/login">Login</a>
        </p>
      </form>
    </div>
  );
};

export default Register;
