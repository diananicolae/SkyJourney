package com.pub.skyjourney.core.model

enum class Airport(private val code: String, private val city: String) {
    LAX("LAX", "Los Angeles"),
    JFK("JFK", "New York"),
    ORD("ORD", "Chicago"),
    ATL("ATL", "Atlanta"),
    LHR("LHR", "London"),
    CDG("CDG", "Paris"),
    DXB("DXB", "Dubai"),
    HND("HND", "Tokyo"),
    SYD("SYD", "Sydney"),
    FRA("FRA", "Frankfurt");

    override fun toString(): String {
        return "$code - $city"
    }

    companion object {
        fun fromCode(code: String): Airport {
            return entries.firstOrNull { it.code == code }
                ?: throw IllegalArgumentException("Invalid airport code: $code")
        }
    }
}
