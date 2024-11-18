package com.pub.skyjourney.service

import com.pub.skyjourney.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MongoUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val applicationUser = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User '$username' not found")

        return User.builder()
            .username(applicationUser.username)
            .password(applicationUser.password)
            // .roles(...) // Define roles if necessary
            .build()
    }
}