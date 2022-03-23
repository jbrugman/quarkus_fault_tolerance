package com.jdriven.services

import io.quarkus.test.junit.QuarkusTest
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
class CircuitBreaker {

    @Inject
    lateinit var pingService: PingService

    @Test
    fun testCircuitBreakerOpens() {

        pingService.ping(true)

        for (i in 1 until 4) {
            try {
                pingService.ping(false)
                fail()
            } catch (expected: IllegalStateException) {

                println(expected.message)
            }
        }

        // Circuit should be open
        try {
            pingService.ping(true)
            fail()
        } catch (expected: CircuitBreakerOpenException) {
            println(expected.message)
        }

        assertEquals(4, pingService.pingCounter.get())
    }
}
