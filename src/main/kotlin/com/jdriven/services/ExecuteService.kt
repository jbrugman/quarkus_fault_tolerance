package com.jdriven.services

import org.eclipse.microprofile.faulttolerance.Fallback
import org.eclipse.microprofile.faulttolerance.Retry
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


@ApplicationScoped
class ExecuteService {

    @Inject
    lateinit var canFailService: CanFailService

    @Retry(maxRetries = 4)
    @Fallback(fallbackMethod = "stable")
    fun executeMe(i : Int) : Int{
        return canFailService.unstable( i )
    }

    private fun stable( i: Int ) : Int {
        return 10
    }

}