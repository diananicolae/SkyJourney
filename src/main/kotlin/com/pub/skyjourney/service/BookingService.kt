package com.pub.skyjourney.service

import com.pub.skyjourney.model.PaymentStatus
import com.pub.skyjourney.model.Booking
import com.pub.skyjourney.model.BookingStatus
import com.pub.skyjourney.model.CreateBookingRequest
import com.pub.skyjourney.repository.BookingRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val paymentService: PaymentService
) {

    fun createBooking(request: CreateBookingRequest): Booking {
        val paymentResponse = paymentService
            .makePayment(request.paymentRequest)

        if (paymentResponse.status == PaymentStatus.FAILED) {
            throw RuntimeException("Payment failed!")
        }

        val booking = Booking(
            UUID.randomUUID().toString(),
            request.userId,
            request.flightId,
            request.seat,
            paymentResponse.paymentId,
            BookingStatus.CONFIRMED
        )

        bookingRepository.save(booking)
        return booking
    }

    fun getAllBookings(): List<Booking> {
        return bookingRepository.findAll()
    }

    fun getBookingById(bookingId: String): Booking? {
        return bookingRepository.findById(bookingId).getOrNull()
    }

    fun getBookingsByUserId(userId: String): List<Booking> {
        return bookingRepository.findByUserId(userId)
    }

    fun cancelBooking(bookingId: String): Booking {
        val booking = bookingRepository.findById(bookingId).getOrNull() ?: throw RuntimeException("Booking not found")

        if (booking.status == BookingStatus.CHECKED_IN) {
            throw RuntimeException("Canceling is unavailable! Check-in has been completed.")
        }

        if (booking.status == BookingStatus.CANCELLED) {
            throw RuntimeException("Canceling is unavailable! Booking has already been cancelled.")
        }

        return bookingRepository.findById(bookingId).map {
            val updated = it.copy(status = BookingStatus.CANCELLED)
            bookingRepository.save(updated)
        }.get()
    }

    fun checkInBooking(bookingId: String): Booking {
        val booking = bookingRepository.findById(bookingId).getOrNull() ?: throw RuntimeException("Booking not found")

        if (booking.status == BookingStatus.CANCELLED) {
            throw RuntimeException("Check-in is unavailable! Booking has been cancelled.")
        }

        if (booking.status == BookingStatus.CHECKED_IN) {
            throw RuntimeException("Check-in is unavailable! Check-in has already been completed.")
        }

        return bookingRepository.findById(bookingId).map {
            val updated = it.copy(status = BookingStatus.CHECKED_IN)
            bookingRepository.save(updated)
        }.get()
    }

    fun deleteAllBookings() {
        bookingRepository.deleteAll()
    }
}