package com.pub.skyjourney.payment.model

data class PaymentResponse(
    val paymentId: String,
    val clientSecret: String
)