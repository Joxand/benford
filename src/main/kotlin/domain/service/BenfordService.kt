package com.johan.domain.service

import com.johan.domain.model.BenfordCheckResult
import com.johan.domain.model.DigitDistributionInput
import com.johan.domain.model.RawTextInput
import org.apache.commons.math3.stat.inference.ChiSquareTest

class BenfordService {

    /**
     * Extracts numbers from a text input and evaluates whether their first-digit
     * distribution follows Benford's Law at the specified
     * significance level.
     *
     * @param input input object containing the raw text to analyze and the
     * significance level.
     *
     * @return a [BenfordCheckResult] containing the observed distribution,
     * expected distribution, and a decision indicating whether the data
     * follows Benford’s Law.
     */
    fun check(input: RawTextInput): BenfordCheckResult {
        val observed = input.text.extractBenfordNumbers();

        return check(DigitDistributionInput(observed, input.significanceLevel))
    }

    /**
     * Performs a Benford's Law check on a precomputed distribution of first digits.
     *
     * The result indicates whether the data follows Benford's Law at the specified
     * significance level.
     *
     * @param input input object containing  an array of observed counts for digits 1–9 and the
     * significance level.
     *
     * @return a [BenfordCheckResult] containing the observed distribution,
     * expected distribution, and a decision indicating whether the data
     * follows Benford’s Law.
     */
    fun check(input: DigitDistributionInput): BenfordCheckResult {
        val totalNumbers = input.observed.sum()
        val expected = BENFORD_PROBABILITIES
            .map { it * totalNumbers }
            .toDoubleArray()

        return BenfordCheckResult(
            observedDistribution = (1..9).associateWith { digit -> input.observed[digit - 1] },
            expectedDistribution = (1..9).associateWith { digit -> expected[digit - 1] },
            followsBenford = ChiSquareTest().chiSquareTest(expected, input.observed) >= input.significanceLevel
        )
    }

    companion object {
        private val BENFORD_PROBABILITIES = doubleArrayOf(
            0.301, 0.176, 0.125, 0.097, 0.079, 0.067, 0.058, 0.051, 0.046
        )

        fun expectedDistribution(totalNumbers: Long): DoubleArray {
            return BENFORD_PROBABILITIES
                .map { it * totalNumbers }
                .toDoubleArray()
        }
    }
}

class BenfordInputValidationException(message: String) : RuntimeException(message)
