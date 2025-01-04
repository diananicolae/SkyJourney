package com.pub.skyjourney.core.filter

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Component
class RequestLoggingFilter : Filter {

    private val logger = LoggerFactory.getLogger(RequestLoggingFilter::class.java)

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (request is HttpServletRequest) {
            logger.info("Request: ${request.method} ${request.requestURI}")
        }

        chain.doFilter(request, response)
    }
}
