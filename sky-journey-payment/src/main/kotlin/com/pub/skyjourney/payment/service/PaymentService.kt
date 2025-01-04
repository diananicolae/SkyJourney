package com.pub.skyjourney.payment.service

import com.pub.skyjourney.payment.model.PaymentRequest
import com.pub.skyjourney.payment.model.PaymentResponse
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PaymentService {

    fun makePayment(request: PaymentRequest): PaymentResponse {
        val paymentIntentParams = PaymentIntentCreateParams.builder()
            .setAmount(request.amount)
            .setCurrency("usd")
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    .setEnabled(true)
                    .build()
            )
            .build()

        val paymentIntent = PaymentIntent.create(paymentIntentParams)

        return PaymentResponse(
            paymentId = UUID.randomUUID().toString(),
            clientSecret = paymentIntent.clientSecret
        )
    }
}