package com.pub.skyjourney.auth.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    private val userDetailsService: MongoUserDetailsService,
) {

    private val secretKey = "yourSecretKey"

    fun generateToken(username: String): String {
        val userDetails = userDetailsService.loadUserByUsername(username)

        val now = System.currentTimeMillis()

        return Jwts.builder()
            .setSubject(userDetails.username)
            .setIssuedAt(Date(now))
            .setExpiration(Date(now + 3600000)) // Token validity (e.g., 1 hour)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()
    }
}
