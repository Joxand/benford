package com.johan.domain.model

import com.johan.domain.service.BenfordInputValidationException
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DigitDistributionInputTest {

    @Test
    fun `creates valid input`() {
        val observed = longArrayOf(30, 18, 12, 10, 8, 7, 6, 5, 4)

        val input = DigitDistributionInput(
            observed = observed,
            significanceLevel = 0.05
        )

        assertContentEquals(observed, input.observed)
        assertEquals(0.05, input.significanceLevel)
    }

    @ParameterizedTest
    @ValueSource(doubles = [0.0, -0.1, 1.0, 1.1])
    fun `throws when significance level is invalid`(significanceLevel: Double) {
        val exception = assertFailsWith<BenfordInputValidationException> {
            DigitDistributionInput(
                observed = validObserved(),
                significanceLevel = significanceLevel
            )
        }

        assertEquals("significanceLevel must be between 0 and 1", exception.message)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 8, 10])
    fun `throws when observed array does not contain exactly 9 entries`(arraySize: Int) {
        val observed = LongArray(arraySize)
        val exception = assertFailsWith<BenfordInputValidationException> {
            DigitDistributionInput(
                observed = observed,
                significanceLevel = 0.05
            )
        }

        assertEquals("Observed array must contain exactly 9 entries", exception.message)
    }

    @Test
    fun `throws when observed is empty`() {
        val exception = assertFailsWith<BenfordInputValidationException> {
            DigitDistributionInput(
                observed = longArrayOf(),
                significanceLevel = 0.05
            )
        }

        assertEquals("Observed array must contain exactly 9 entries", exception.message)
    }

    @Test
    fun `throws when there are no observed numbers`() {
        val exception = assertFailsWith<BenfordInputValidationException> {
            DigitDistributionInput(
                observed = LongArray(9),
                significanceLevel = 0.05
            )
        }

        assertEquals("At least one observed number is required", exception.message)
    }

    private fun validObserved(): LongArray =
        longArrayOf(30, 18, 12, 10, 8, 7, 6, 5, 4)
}