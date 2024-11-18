package com.pub.skyjourney.model

data class PaymentRequest(
    val userId: String,
    val amount: Double,
    val method: PaymentMethod
)