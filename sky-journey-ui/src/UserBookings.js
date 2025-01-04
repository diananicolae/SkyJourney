import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './UserBookings.css';

import Modal from './Modal';

function UserBookings() {
  const [bookings, setBookings] = useState([]);
  const userId = localStorage.getItem('userId');

  const fetchBookings = async () => {
    try {
      const response = await axios.get(`${process.env.REACT_APP_CORE_URL}/bookings/user/${userId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });
      setBookings(response.data);
    } catch (error) {
      console.error('Error fetching bookings:', error);
    }
  };

  useEffect(() => {
    fetchBookings();

    const interval = setInterval(() => {
      fetchBookings();
    }, 5000);

    return () => clearInterval(interval);
  }, [userId]);

  const [modalShow, setModalShow] = useState(false);
  const [modalMessage, setModalMessage] = useState('');

  const showModal = (message) => {
    setModalMessage(message);
    setModalShow(true);
  };

  const handleCheckIn = async (bookingId) => {
    try {
      await axios.put(`${process.env.REACT_APP_CORE_URL}/bookings/${bookingId}/check-in`, {}, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });
    } catch (error) {
      showModal(`${error.response.data}`);
    }
  };

  const handleCancel = async (bookingId) => {
    try {
      await axios.put(`${process.env.REACT_APP_CORE_URL}/bookings/${bookingId}/cancel`, {}, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });
    } catch (error) {
      showModal(`${error.response.data}`);
    }
  };


  return (
    <div className="my-bookings-container">
      <h2>My Bookings</h2>
      <ul className="my-bookings-list">
        {bookings.map(booking => (
          <li key={booking.bookingId}>
            <p className="important-detail">Booking ID: {booking.bookingId}</p>
            <p>Flight ID: {booking.flightId}</p>
            <p>Seat: {booking.seat}</p>
            <p>Payment ID: {booking.paymentId}</p>
            <p className={
                booking.status === "CONFIRMED" ? 'status-confirmed' :
                booking.status === "CHECKED_IN" ? 'status-checked-in' :
                booking.status ==="CANCELLED" ? 'status-cancelled' : ''
            }>
                Status: {booking.status}
            </p>

            <button 
              onClick={() => handleCheckIn(booking.bookingId)} 
              className="check-in-button">
              Check-In
            </button>
            <button 
              onClick={() => handleCancel(booking.bookingId)} 
              className="cancel-button">
              Cancel
            </button>
          </li>
        ))}
      </ul>

      <Modal show={modalShow} onClose={() => setModalShow(false)}>
        <p>{modalMessage}</p>
      </Modal>
    </div>
  );  
}

export default UserBookings;
