package com.pub.skyjourney.core.controller

import com.pub.skyjourney.core.model.booking.Booking
import com.pub.skyjourney.core.model.booking.CreateBookingRequest
import com.pub.skyjourney.core.service.BookingService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    path = ["/bookings"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@CrossOrigin(origins = ["*"])
class BookingController(
    private val bookingService: BookingService
) {

    @PostMapping("/create")
    fun createBooking(
        @RequestBody request: CreateBookingRequest
    ): ResponseEntity<Booking> {
        val booking = bookingService.createBooking(request)

        return ResponseEntity(booking, HttpStatus.CREATED)
    }

    @GetMapping
    fun getBookings(): ResponseEntity<List<Booking>> {
        val bookings = bookingService.getAllBookings()

        return ResponseEntity.ok(bookings)
    }

    @GetMapping("/{bookingId}")
    fun getBooking(
        @PathVariable bookingId: String
    ): ResponseEntity<Booking> {
        val booking = bookingService.getBookingById(bookingId) ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(booking)
    }

    @PutMapping("/{bookingId}/cancel")
    fun cancelBooking(
        @PathVariable bookingId: String
    ): ResponseEntity<Booking> {
        val booking = bookingService.cancelBooking(bookingId)

        return ResponseEntity.ok(booking)
    }

    @PutMapping("/{bookingId}/check-in")
    fun checkInBooking(
        @PathVariable bookingId: String
    ): ResponseEntity<Booking> {
        val booking = bookingService.checkInBooking(bookingId)

        return ResponseEntity.ok(booking)
    }

    @DeleteMapping
    fun deleteBookings(): ResponseEntity<Void> {
        bookingService.deleteAllBookings()

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/user/{userId}")
    fun getUserBookings(
        @PathVariable userId: String
    ): ResponseEntity<List<Booking>> {
        val bookings = bookingService.getBookingsByUserId(userId)

        return ResponseEntity.ok(bookings)
    }
}