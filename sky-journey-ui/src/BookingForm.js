import React, { useState } from 'react';
import { loadStripe } from '@stripe/stripe-js';
import { Elements } from '@stripe/react-stripe-js';
import './BookingForm.css';
import PaymentForm from './PaymentForm';

const stripePromise = loadStripe("pk_test_51FrQwlBnheRwo4jaGI5iqBTAA9Z9KnwBOOCiNoTMhhLsox5vKpFPB8s61gacy9H4kQZ0Jol31w1KpAHtuS7MKO1100ZOqM7qyt", {
  locale: "en",
});

function BookingForm({ booking, onClose, onChange, onSubmit }) {
  const [showPaymentForm, setShowPaymentForm] = useState(false);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    onChange({ ...booking, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setShowPaymentForm(true);
  };

  const handlePaymentSuccess = (paymentId) => {
    onSubmit(booking, paymentId);
    setShowPaymentForm(false);
  };

  return (
    <div className="booking-modal">
      <div className="booking-form">
        <h2>Book Flight</h2>
        {showPaymentForm ? (
          <Elements stripe={stripePromise} >
            <PaymentForm amount={booking.amount} onPaymentSuccess={handlePaymentSuccess} />
          </Elements>
        ) : (
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="flightId">Flight ID</label>
              <input type="text" id="flightId" value={booking.flightId} readOnly />
            </div>

            <div className="form-group">
              <label htmlFor="amount">Price</label>
              <input type="text" id="amount" value={`$${booking.amount}`} readOnly />
            </div>

            <div className="form-group">
              <label htmlFor="seat">Seat</label>
              <select name="seat" id="seat" value={booking.seat} onChange={handleInputChange}>
                <option value="MIDDLE">Middle</option>
                <option value="WINDOW">Window</option>
                <option value="AISLE">Aisle</option>
              </select>
            </div>

            <button type="submit">Confirm</button>
          </form>
        )}
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
}

export default BookingForm;
