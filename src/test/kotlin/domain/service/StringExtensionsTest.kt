package com.johan.domain.service

import kotlin.test.Test
import kotlin.test.assertContentEquals

class StringExtensionsTest {

    @Test
    fun `extractBenfordDigits returns first digits`() {
        val input = "Invoice 123.45 payment 45 refund 302 amount 987"

        val result = input.extractBenfordNumbers()

        assertContentEquals(longArrayOf(1, 0, 1, 1, 0, 0, 0, 0, 1), result)
    }

    @Test
    fun `extractBenfordDigits ignores minus sign`() {
        val input = "Values: -123 -45.67 -302 -987"

        val result = input.extractBenfordNumbers()

        assertContentEquals(longArrayOf(1, 0, 1, 1, 0, 0, 0, 0, 1), result)
    }

    @Test
    fun `extractBenfordDigits skips zeros`() {
        val input = "Values: 0 00 -0 000.0"

        val result = input.extractBenfordNumbers()

        assertContentEquals(LongArray(9), result)
    }

    @Test
    fun `extractBenfordDigits handles negative numbers`() {
        val input = "Values: 0.045 0.9 -0.0032"

        val result = input.extractBenfordNumbers()

        assertContentEquals(longArrayOf(0, 0, 1, 1, 0, 0, 0, 0, 1), result)
    }

    @Test
    fun `extractBenfordDigits returns empty list when there are no numbers`() {
        val input = "There are no numeric values here"

        val result = input.extractBenfordNumbers()

        assertContentEquals(LongArray(9), result)
    }

    @Test
    fun `extractBenfordDigits keeps repeated digits`() {
        val input = "10 12 199 25 29 333"

        val result = input.extractBenfordNumbers()

        assertContentEquals(longArrayOf(3, 2, 1, 0, 0, 0, 0, 0, 0), result)
    }
}