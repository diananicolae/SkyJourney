import React, { useState } from 'react';
import axios from 'axios';
import { useStripe, useElements, CardElement } from '@stripe/react-stripe-js';

const PaymentForm = ({ amount, onPaymentSuccess }) => {
  const stripe = useStripe();
  const elements = useElements();
  const [message, setMessage] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!stripe || !elements) return;

    const card = elements.getElement(CardElement);
    const result = await stripe.createPaymentMethod({
      type: 'card',
      card: card,
    });

    if (result.error) {
      setMessage(result.error.message);
    } else {
      const userId = localStorage.getItem('userId');

      const response = await axios.post(`${process.env.REACT_APP_PAYMENT_URL}/payments/create`, {
        userId: userId,
        amount: amount
      }, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });

      const { paymentId, clientSecret } = response.data;

      const confirmResult = await stripe.confirmCardPayment(clientSecret, {
        payment_method: result.paymentMethod.id,
      });

      if (confirmResult.error) {
        setMessage(confirmResult.error.message);
      } else if (confirmResult.paymentIntent.status === 'succeeded') {
        setMessage('Payment successful!');
        setTimeout(() => {
            onPaymentSuccess(paymentId);
          }, 1000);
      }
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <CardElement />
      <button type="submit" disabled={!stripe}>
        Pay ${amount}
      </button>
      {message && <div>{message}</div>}
    </form>
  );
};

export default PaymentForm;