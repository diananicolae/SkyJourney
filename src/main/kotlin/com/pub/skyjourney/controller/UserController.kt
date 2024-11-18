package com.pub.skyjourney.controller

import com.pub.skyjourney.model.ApplicationUser
import com.pub.skyjourney.model.CreateUserRequest
import com.pub.skyjourney.service.UserService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    path = ["/users"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@CrossOrigin(origins = ["*"])
class UserController(
    private val userService: UserService
) {

    @PostMapping("/create-account")
    fun createAccount(@RequestBody createUserRequest: CreateUserRequest): ResponseEntity<ApplicationUser> {
        if (createUserRequest.username.isBlank() || createUserRequest.password.isBlank() || createUserRequest.name.isBlank()) {
            return ResponseEntity.badRequest().build()
        }

        val user = userService.getUserByUsername(createUserRequest.username)

        if (user != null) {
            return ResponseEntity.badRequest().build()
        }

        return ResponseEntity.ok(userService.createUser(createUserRequest))
    }

    @GetMapping
    fun getUsers(): ResponseEntity<List<ApplicationUser>> {
        val users = userService.getAllUsers()

        return ResponseEntity.ok(users)
    }
}