// src/Routes.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Login';
import Register from './Register';
import AppointmentForm from './AppointmentForm';
import BookingList from './BookingList';
import UpdateAppointment from './UpdateAppointment';
import Home from './Home'

const AppRoutes = () => (
  <Router>
    <Routes>
      <Route path="/" element={<Home/>}></Route>
     <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path='/appointment' element={<AppointmentForm/>}/>
      <Route path='/BookingList' element={<BookingList/>}/>
      <Route path='/update/:id' element={<UpdateAppointment/>}/>
    </Routes>
  </Router>
);

export default AppRoutes;
