package com.pub.skyjourney.model

data class CreateBookingRequest(
    val userId: String,
    val flightId: String,
    val seat: String,
    val paymentRequest: PaymentRequest,
)