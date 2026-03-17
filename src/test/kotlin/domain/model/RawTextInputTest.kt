package com.johan.domain.model

import com.johan.domain.service.BenfordInputValidationException
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class RawTextInputTest {

    @ParameterizedTest
    @ValueSource(doubles = [0.0, -0.1, 1.0, 1.1])
    fun `throws when significance level is invalid`(significanceLevel: Double) {
        val exception = assertFailsWith<BenfordInputValidationException> {
            RawTextInput(
                text = "Invoice 123",
                significanceLevel = significanceLevel
            )
        }

        assertEquals("significanceLevel must be between 0 and 1", exception.message)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "   ", "\n", "\t", "\n\t  "])
    fun `throws when text is blank`(text: String) {
        val exception = assertFailsWith<BenfordInputValidationException> {
            RawTextInput(
                text = text,
                significanceLevel = 0.05
            )
        }

        assertEquals("text must not be blank", exception.message)
    }

    @Test
    fun `creates valid input`() {
        val input = RawTextInput(
            text = "Invoice 123 payment 45",
            significanceLevel = 0.5
        )

        assertEquals("Invoice 123 payment 45", input.text)
        assertEquals(0.5, input.significanceLevel)
    }
}