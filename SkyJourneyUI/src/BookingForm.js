import React from 'react';
import './BookingForm.css';

function BookingForm({ booking, onClose, onBook, onSubmit }) {
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    onBook({ ...booking, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(booking);
  };

  return (
    <div className="booking-modal">
      <div className="booking-form">
        <h2>Book Flight</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="flightId">Flight ID</label>
            <input type="text" id="flightId" value={booking.flightId} readOnly />
          </div>

          <div className="form-group">
            <label htmlFor="seat">Seat</label>
            <input type="text" name="seat" id="seat" value={booking.seat} onChange={handleInputChange} />
          </div>

          <div className="form-group">
            <label htmlFor="amount">Price</label>
            <input type="text" id="amount" value={booking.amount} readOnly />
          </div>

          <div className="form-group">
            <label htmlFor="method">Payment Method</label>
            <select name="method" id="method" value={booking.method} onChange={handleInputChange}>
              <option value="CREDIT_CARD">Credit Card</option>
              <option value="DEBIT_CARD">Debit Card</option>
              <option value="PAYPAL">PayPal</option>
            </select>
          </div>
          <button type="submit">Submit Booking</button>
        </form>
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
}

export default BookingForm;
