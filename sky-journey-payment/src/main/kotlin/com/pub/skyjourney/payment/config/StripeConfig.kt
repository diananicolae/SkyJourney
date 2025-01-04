package com.pub.skyjourney.payment.config

import com.stripe.Stripe
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class StripeConfig {
    @Value("\${stripe.api.key}")
    lateinit var apiKey: String

    @PostConstruct
    fun init() {
        Stripe.apiKey = apiKey
    }
}
