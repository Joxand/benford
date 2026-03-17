package com.johan.domain.service

import com.johan.domain.model.DigitDistributionInput
import com.johan.domain.model.RawTextInput
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BenfordServiceTest {

    private val service = BenfordService()

    @Test
    fun `check with observed array returns expected metadata`() {
        val observed = longArrayOf(30, 18, 12, 10, 8, 7, 6, 5, 4)

        val result = service.check(DigitDistributionInput(observed, 0.05))

        assertEquals(30L, result.observedDistribution[1])
        assertEquals(18L, result.observedDistribution[2])
        assertEquals(12L, result.observedDistribution[3])
        assertEquals(10L, result.observedDistribution[4])
        assertEquals(8L, result.observedDistribution[5])
        assertEquals(7L, result.observedDistribution[6])
        assertEquals(6L, result.observedDistribution[7])
        assertEquals(5L, result.observedDistribution[8])
        assertEquals(4L, result.observedDistribution[9])

        assertEquals(30.1, result.expectedDistribution[1]!!, 0.0001)
        assertEquals(17.6, result.expectedDistribution[2]!!, 0.0001)
        assertEquals(12.5, result.expectedDistribution[3]!!, 0.0001)
        assertEquals(9.7, result.expectedDistribution[4]!!, 0.0001)
        assertEquals(7.9, result.expectedDistribution[5]!!, 0.0001)
        assertEquals(6.7, result.expectedDistribution[6]!!, 0.0001)
        assertEquals(5.8, result.expectedDistribution[7]!!, 0.0001)
        assertEquals(5.1, result.expectedDistribution[8]!!, 0.0001)
        assertEquals(4.6, result.expectedDistribution[9]!!, 0.0001)

        assertTrue(result.followsBenford)
    }

    @Test
    fun `check with observed array that matches Benford closely follows benford`() {
        val observed = longArrayOf(301, 176, 125, 97, 79, 67, 58, 51, 46)

        val result = service.check(DigitDistributionInput(observed, 0.05))

        assertTrue(result.followsBenford)
    }

    @Test
    fun `check with clearly non benford observed array does not follow benford`() {
        val observed = longArrayOf(100, 0, 0, 0, 0, 0, 0, 0, 0)

        val result = service.check(DigitDistributionInput(observed, 0.05))

        assertFalse(result.followsBenford)
    }

    @Test
    fun `check from input parses digits and counts correctly`() {
        val input = RawTextInput(
            text = "Invoice 123.45 payment 45 refund 302 amount 987",
            significanceLevel = 0.05
        )

        val result = service.check(input)

        assertEquals(1L, result.observedDistribution[1])
        assertEquals(0L, result.observedDistribution[2])
        assertEquals(1L, result.observedDistribution[3])
        assertEquals(1L, result.observedDistribution[4])
        assertEquals(0L, result.observedDistribution[5])
        assertEquals(0L, result.observedDistribution[6])
        assertEquals(0L, result.observedDistribution[7])
        assertEquals(0L, result.observedDistribution[8])
        assertEquals(1L, result.observedDistribution[9])
    }

    @Test
    fun `check from input handles decimals below one and negatives`() {
        val input = RawTextInput(
            text = "Values: -0.045 -12 0.9 300",
            significanceLevel = 0.05
        )

        val result = service.check(input)

        assertEquals(1L, result.observedDistribution[1])
        assertEquals(0L, result.observedDistribution[2])
        assertEquals(1L, result.observedDistribution[3])
        assertEquals(1L, result.observedDistribution[4])
        assertEquals(0L, result.observedDistribution[5])
        assertEquals(0L, result.observedDistribution[6])
        assertEquals(0L, result.observedDistribution[7])
        assertEquals(0L, result.observedDistribution[8])
        assertEquals(1L, result.observedDistribution[9])
    }

}