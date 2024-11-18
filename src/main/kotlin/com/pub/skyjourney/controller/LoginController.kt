package com.pub.skyjourney.controller

import com.pub.skyjourney.model.LoginRequest
import com.pub.skyjourney.model.LoginResponse
import com.pub.skyjourney.service.TokenService
import com.pub.skyjourney.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping(
    path = ["/login"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@CrossOrigin(origins = ["*"])
class LoginController(
    val authenticationManager: AuthenticationManager,
    val userService: UserService,
    val tokenService: TokenService
) {

    @PostMapping
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        if (loginRequest.username.isBlank() || loginRequest.password.isBlank()) {
            return ResponseEntity.badRequest().build()
        }

        val authenticationRequest = UsernamePasswordAuthenticationToken
            .unauthenticated(loginRequest.username, loginRequest.password)

        val authenticationResponse = authenticationManager.authenticate(authenticationRequest)

        if (authenticationResponse.isAuthenticated) {
            val token = tokenService.generateToken(loginRequest.username)
            val user = userService.getUserByUsername(loginRequest.username)

            if (user != null) {
                return ResponseEntity.ok(LoginResponse(token, user.id!!, user.name))
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }
}