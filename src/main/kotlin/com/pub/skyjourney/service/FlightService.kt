package com.pub.skyjourney.service

import com.pub.skyjourney.model.Airline
import com.pub.skyjourney.model.Airport
import com.pub.skyjourney.model.Flight
import com.pub.skyjourney.repository.FlightRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class FlightService(
    private val flightRepository: FlightRepository
) {

    fun getFlights(): List<Flight> {
        return flightRepository.getFlights()
    }

    fun getFilteredFlights(
        origin: String,
        destination: String,
        airlines: List<String>?,
        date: LocalDate?
    ): List<Flight> {
        return flightRepository.findFilteredFlights(
            Airport.fromCode(origin),
            Airport.fromCode(destination),
            Airline.fromName(airlines),
            date
        )
    }

    fun getFlightById(flightId: String): Flight {
        return flightRepository.findFlightById(flightId)
    }
}