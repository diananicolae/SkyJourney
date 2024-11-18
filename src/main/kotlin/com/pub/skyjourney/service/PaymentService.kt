package com.pub.skyjourney.service

import com.pub.skyjourney.model.PaymentRequest
import com.pub.skyjourney.model.PaymentResponse
import com.pub.skyjourney.model.PaymentStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PaymentService {

    fun makePayment(request: PaymentRequest): PaymentResponse {
        return PaymentResponse(
            paymentId = UUID.randomUUID().toString(),
            userId = request.userId,
            status = PaymentStatus.SUCCESS
        )
    }
}