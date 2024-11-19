import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import './App.css';

import FlightSearch from './FlightSearch';
import UserBookings from './UserBookings';
import LoginForm from './LoginForm';
import CreateAccount from './CreateAccount';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userName, setUserName] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const storedUserName = localStorage.getItem('userName');
    setIsLoggedIn(!!token && !!userId);
    if (storedUserName) {
      setUserName(storedUserName);
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('userName');
    window.location.href = '/';
    setIsLoggedIn(false);
  };

  return (
    <Router>
      <div className="App">
        <h1><i className="fas fa-plane title-icon"></i>SkyJourney - Flight Booking Application</h1>

        <nav className="navigation-tab">
          <div className="nav-left">
            <Link to="/flights/search" className="nav-button">Search Flights</Link>
            <Link to="/user/bookings" className="nav-button">My Bookings</Link>
          </div>
          <div className="nav-right">
            {isLoggedIn ? (
              <>
                <span className="nav-text"><i className="fas fa-user title-icon"></i>Hello, {userName}</span>
                <button onClick={handleLogout} className="logout-button">Log Out</button>
              </>
              ) : (
                <>
                  <Link to="/user/login" className="nav-button">Log In</Link>
                  <Link to="/user/create-account" className="nav-button">Create Account</Link>
                </>
            )}
          </div>
        </nav>

        <Routes>
          <Route path="/flights/search" element={<FlightSearch />} />
          <Route path="/user/bookings" element={<UserBookings />} />
          <Route path="/user/login" element={<LoginForm setIsLoggedIn={setIsLoggedIn} />} />
          <Route path="/user/create-account" element={<CreateAccount setIsLoggedIn={setIsLoggedIn} />} />
        </Routes>
      </div>
    </Router>
      
  );
}

export default App;
