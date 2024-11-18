package com.pub.skyjourney.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Booking(
    @Id
    val bookingId: String,
    val userId: String,
    val flightId: String,
    val seat: String,
    val paymentId: String,
    val status: BookingStatus
)
