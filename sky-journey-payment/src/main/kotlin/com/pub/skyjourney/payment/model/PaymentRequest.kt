package com.pub.skyjourney.payment.model

data class PaymentRequest(
    val userId: String,
    val amount: Long
)