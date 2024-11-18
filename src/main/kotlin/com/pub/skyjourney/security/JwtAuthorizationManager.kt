package com.pub.skyjourney.security

import com.pub.skyjourney.service.TokenValidationService
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.stereotype.Component
import java.util.function.Supplier

@Component
class JwtAuthorizationManager(
    private val tokenValidationService: TokenValidationService
) : AuthorizationManager<RequestAuthorizationContext> {

    override fun check(
        authentication: Supplier<Authentication>,
        context: RequestAuthorizationContext
    ): AuthorizationDecision {
        val auth = authentication.get()

        if (!auth.isAuthenticated) {
            return AuthorizationDecision(false)
        }

        val token = tokenValidationService.extractToken(context.request)

        if (token != null && tokenValidationService.validateToken(token)) {
            return AuthorizationDecision(true)
        }

        return AuthorizationDecision(false)
    }

}