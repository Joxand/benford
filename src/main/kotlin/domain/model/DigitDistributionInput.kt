package com.johan.domain.model

import com.johan.domain.service.BenfordInputValidationException

data class DigitDistributionInput(
    val observed: LongArray,
    val significanceLevel: Double
) {
    init {
        if (significanceLevel <= 0.0 || significanceLevel >= 1.0) {
            throw BenfordInputValidationException("significanceLevel must be between 0 and 1")
        }
        if (observed.size != 9) {
            throw BenfordInputValidationException("Observed array must contain exactly 9 entries")
        }
        if (observed.sum() <= 0) {
            throw BenfordInputValidationException("At least one observed number is required")
        }
    }
}