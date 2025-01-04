package com.pub.skyjourney.core.model.booking

data class CreateBookingRequest(
    val userId: String,
    val flightId: String,
    val seat: String,
    val paymentId: String
)