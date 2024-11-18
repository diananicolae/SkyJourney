package com.pub.skyjourney.repository

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.pub.skyjourney.model.Airline
import com.pub.skyjourney.model.Airport
import com.pub.skyjourney.model.Flight
import jakarta.annotation.PostConstruct
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class FlightRepository {

    private lateinit var flights: List<Flight>

    @PostConstruct
    fun loadFlightsData() {
        val flightsInput = ClassPathResource("flights.json").inputStream
        flights = jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .readValue(flightsInput, Array<Flight>::class.java).toList()
    }

    fun getFlights(): List<Flight> = flights

    fun findFilteredFlights(
        origin: Airport,
        destination: Airport,
        airlines: List<Airline>,
        date: LocalDate?
    ): List<Flight> {
        return flights.filter {
            it.origin == origin && it.destination == destination
        }.filter {
            if (airlines.isEmpty()) true else airlines.contains(it.airline)
        }.filter {
            if (date == null) true else it.date == date
        }
    }

    fun findFlightById(flightId: String): Flight {
        return flights.first { it.flightId == flightId }
    }
}
