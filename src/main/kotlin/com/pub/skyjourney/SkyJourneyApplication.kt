package com.pub.skyjourney

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SkyJourneyApplication

fun main(args: Array<String>) {
    runApplication<SkyJourneyApplication>(*args)
}
