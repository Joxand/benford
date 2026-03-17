package com.johan.domain.service

private val NUMBER_REGEX = Regex("""-?\d+(\.\d+)?""")

/**
 * Extracts all numeric values and computes the distribution
 * of their first significant digits according to Benford's Law.
 *
 * Example:
 * "Invoice 123, amount 45, refund 302" → [1,2,2,1,1,0,0,0,0]
 *
 * @return a [LongArray] of size 9 representing counts of digits 1–9
 */
fun String.extractBenfordNumbers(): LongArray {
    val benfordNumbers = LongArray(9)
    NUMBER_REGEX.findAll(this)
        .mapNotNull { match -> match.value.firstBenfordDigit() }
        .forEach { digit -> benfordNumbers[digit - 1]++ }

    return benfordNumbers;
}

private fun String.firstBenfordDigit(): Int? =
    firstOrNull { it in '1'..'9' }?.digitToInt()