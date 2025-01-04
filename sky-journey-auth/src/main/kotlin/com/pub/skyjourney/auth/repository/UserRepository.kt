package com.pub.skyjourney.auth.repository

import com.pub.skyjourney.auth.model.ApplicationUser
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<ApplicationUser, String> {
    fun findByUsername(username: String): ApplicationUser?
}