import React from 'react';
import './Home.css';

const Home = () => {
    return (
        <div className="home">
            <header className="header">
                <div className="container-fluid d-flex justify-content-between align-items-center py-2">
                </div>
            </header>
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <div className="navContainer">
                    <a className="navbar-brand" href='/home'>CPPE1-WELLESS-CENTER</a>
                    <div className="nav-list" id="navbarNav">
                        {/* <ul className="navbar-nav ml-auto">
                            <li className="nav-item active">
                                <a className="nav-link" href="#">Home</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="#">About Us</a>
                            </li>
                        </ul> */}
                        
                       
                    </div>
                </div>
            </nav> 

            <div className="hero">
                <div className="hero-container">
                    <h2 className="hero-title">Transforming Lives Through Wellness</h2>
                    <p className="hero-subtitle">Where Health and Happiness Meet</p>
                    <div className='login-button-container'>
                        <a href="/login" className="btn btn-primary login-button">Login</a>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Home;
