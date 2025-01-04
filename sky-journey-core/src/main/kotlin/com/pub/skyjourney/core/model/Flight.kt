package com.pub.skyjourney.core.model

import java.time.LocalDate

data class Flight(
    val flightId: String,
    val airline: Airline,
    val origin: Airport,
    val destination: Airport,
    val date: LocalDate,
    val duration: String,
    val price: Long
)
