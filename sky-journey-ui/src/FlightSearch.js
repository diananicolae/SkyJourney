import React, { useState } from 'react';
import axios from 'axios';
import Select from 'react-select';
import './FlightSearch.css';
import BookingForm from './BookingForm';
import Modal from './Modal';

const airportOptions = [
  { value: "LAX", label: "Los Angeles International Airport" },
  { value: "JFK", label: "John F. Kennedy International Airport" },
  { value: "ORD", label: "O'Hare International Airport" },
  { value: "ATL", label: "Hartsfield-Jackson Atlanta International Airport" },
  { value: "LHR", label: "Heathrow Airport" },
  { value: "CDG", label: "Charles de Gaulle Airport" },
  { value: "DXB", label: "Dubai International Airport" },
  { value: "HND", label: "Tokyo Haneda Airport" },
  { value: "SYD", label: "Sydney Airport" },
  { value: "FRA", label: "Frankfurt Airport" }
];

const airlineOptions = [
  { value: "EMIRATES", label: "Emirates" },
  { value: "QANTAS", label: "Qantas" },
  { value: "LUFTHANSA", label: "Lufthansa" },
  { value: "DELTA", label: "Delta" },
  { value: "UNITED", label: "United" },
  { value: "AIR_CANADA", label: "Air Canada" },
  { value: "BRITISH_AIRWAYS", label: "British Airways" },
  { value: "AIR_FRANCE", label: "Air France" },
  { value: "AMERICAN_AIRLINES", label: "American Airlines" },
  { value: "SINGAPORE_AIRLINES", label: "Singapore Airlines" }
];

function FlightSearch() {
  const [searchParams, setSearchParams] = useState({
    origin: '',
    destination: '',
    airlines: [],
    date: '',
  });
  const [flights, setFlights] = useState([]);

  const [activeBooking, setActiveBooking] = useState({
    userId: localStorage.getItem('userId'),
    flightId: '',
    seat: '',
    amount: ''
  });

  const [showBookingForm, setShowBookingForm] = useState(false);
  const [modalShow, setModalShow] = useState(false);
  const [modalMessage, setModalMessage] = useState('');

  const showModal = (message) => {
    setModalMessage(message);
    setModalShow(true);
  };

  const openBookingForm = (flight) => {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      showModal('Please log in to book a flight.');
      return;
    }

    setActiveBooking({
      userId: userId,
      flightId: flight.flightId, 
      seat: 'MIDDLE',
      amount: flight.price
    });
    setShowBookingForm(true);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSearchParams({ ...searchParams, [name]: value });
  };

  const handleAirportChange = (selectedOption, actionMeta) => {
    setSearchParams({ ...searchParams, [actionMeta.name]: selectedOption.value });
  };

  const handleAirlinesChange = (selectedOptions) => {
    const selectedAirlines = selectedOptions.map(option => option.value);
    setSearchParams({ ...searchParams, airlines: selectedAirlines.join(',') });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get(`${process.env.REACT_APP_CORE_URL}/flights/search`, { 
        params: searchParams
      });
      setFlights(response.data);
    } catch (error) {
      console.error('Error fetching flights:', error);
    }
  };

  const handleBookingChange = (updatedBooking) => {
    setActiveBooking(updatedBooking);
  };

  const submitBooking = async (bookingData, paymentId) => {
    const payload = {
      userId: bookingData.userId,
      flightId: bookingData.flightId,
      seat: bookingData.seat,
      paymentId: paymentId
    };

    try {
      const response = await axios.post(`${process.env.REACT_APP_CORE_URL}/bookings/create`, payload, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });
  
      // Handle the response (e.g., show a success message)
      console.log(response.data);
      setShowBookingForm(false); // Close the form on success
    } catch (error) {
      console.error('Error creating booking:', error);
    }
  };

  return (
    <div className="flight-search-container">
      <form onSubmit={handleSubmit} className="flight-search-form">

      <Select
          options={airportOptions}
          onChange={(selectedOption) => handleAirportChange(selectedOption, { name: 'origin' })}
          placeholder="Select Origin"
          className="airport-select"
          isClearable
        />

        <Select
          options={airportOptions}
          onChange={(selectedOption) => handleAirportChange(selectedOption, { name: 'destination' })}
          placeholder="Select Destination"
          className="airport-select"
          isClearable
        />

        <Select
          options={airlineOptions}
          isMulti
          name="airlines"
          onChange={handleAirlinesChange}
          className="airlines-multi-select"
        />


        <input type="date" name="date" onChange={handleInputChange} value={searchParams.date} />
        <button type="submit">Search Flights</button>
      </form>

      {flights.length > 0 && (
        <ul className="flight-result">
          {flights.map(flight => (
            <li key={flight.flightId}>
              <p className="flight-detail"><strong>Flight ID:</strong> {flight.flightId}</p>
              <p className="flight-detail"><strong>Airline:</strong> {flight.airline}</p>
              <p className="flight-detail"><strong>Origin:</strong> {flight.origin}</p>
              <p className="flight-detail"><strong>Destination:</strong> {flight.destination}</p>
              <p className="flight-detail"><strong>Date:</strong> {flight.date}</p>
              <p className="flight-detail"><strong>Duration:</strong> {flight.duration}</p>
              <p className="flight-detail"><strong>Price:</strong> {`$${flight.price}`}</p>
              <button onClick={() => openBookingForm(flight)}>Book Flight</button>
            </li>
          ))}
        </ul>
       )}

       {showBookingForm && (
             <BookingForm
             booking={activeBooking}
             onClose={() => setShowBookingForm(false)}
             onChange={handleBookingChange}
             onSubmit={submitBooking}
           />
        )}
        
        <Modal show={modalShow} onClose={() => setModalShow(false)}>
          <p>{modalMessage}</p>
        </Modal>
    </div>
  );
}

export default FlightSearch;
