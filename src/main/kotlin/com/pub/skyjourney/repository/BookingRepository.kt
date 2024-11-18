package com.pub.skyjourney.repository

import com.pub.skyjourney.model.Booking
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BookingRepository: MongoRepository<Booking, String> {

    fun findByUserId(userId: String): List<Booking>
}