package com.pub.skyjourney.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class ApplicationUser(
    @Id
    val id: String? = null,
    val name: String,
    val username: String,
    val password: String
)