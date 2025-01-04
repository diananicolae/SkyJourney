package com.pub.skyjourney.payment.controller

import com.pub.skyjourney.payment.model.PaymentRequest
import com.pub.skyjourney.payment.model.PaymentResponse
import com.pub.skyjourney.payment.service.PaymentService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    path = ["/payments"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@CrossOrigin(origins = ["*"])
class PaymentController(
    private val paymentService: PaymentService
) {

    @PostMapping("/create")
    fun makePayment(
        @RequestBody request: PaymentRequest
    ): ResponseEntity<PaymentResponse> {
        val paymentResponse = paymentService.makePayment(request)

        return ResponseEntity.ok(paymentResponse)
    }
}