package com.pub.skyjourney.model

data class PaymentResponse(
    val paymentId: String,
    val userId: String,
    val status: PaymentStatus
)