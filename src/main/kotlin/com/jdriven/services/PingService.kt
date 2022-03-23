package com.jdriven.services

import org.eclipse.microprofile.faulttolerance.CircuitBreaker
import java.util.concurrent.atomic.AtomicInteger
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class PingService {
    val pingCounter = AtomicInteger(0)

    @CircuitBreaker(requestVolumeThreshold = 4)
    fun ping(success: Boolean): String {
        pingCounter.incrementAndGet()
        if (success) {
            return "ok"
        }
        throw IllegalStateException()
    }
}