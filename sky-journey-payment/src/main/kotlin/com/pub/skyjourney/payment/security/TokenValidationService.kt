package com.pub.skyjourney.payment.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.security.SignatureException

@Service
class TokenValidationService {
    private val secretKey = "yourSecretKey"

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return true
        } catch (_: MalformedJwtException) {
        } catch (_: SignatureException) {
        } catch (_: ExpiredJwtException) {
        }

        return false
    }

    fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")

        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}