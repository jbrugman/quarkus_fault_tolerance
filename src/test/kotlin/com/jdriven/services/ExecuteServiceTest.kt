package com.jdriven.services

import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@QuarkusTest
class ExecuteServiceTest {

    @InjectMock
    lateinit var canFailService: CanFailService

    @Inject
    lateinit var  executeService: ExecuteService

    @Test
    fun testUnhappyRetry(){
        Mockito.doThrow(NullPointerException()).`when`(canFailService)?.unstable(1)
        var fallbackValue = executeService.executeMe(1)

        // Expecting to retry, so the unstable function should have been called 5 times:
        Mockito.verify(canFailService, Mockito.times(5)).unstable(1)

        // Should give fallback value 10:
        Assertions.assertEquals(10, fallbackValue)
    }

    @Test
    fun testHappyNoRetry(){
        Mockito.`when`(canFailService.unstable(2)).thenReturn(2)
        var happyValue = executeService.executeMe(2)

        // Expecting no retry needed, so the unstable function should have been called 1 times:
        Mockito.verify(canFailService, Mockito.times(1)).unstable(2)

        // Should give value 2:
        Assertions.assertEquals(2, happyValue)
    }
}