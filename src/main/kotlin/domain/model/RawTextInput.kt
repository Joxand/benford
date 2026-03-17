package com.johan.domain.model

import com.johan.domain.service.BenfordInputValidationException

data class RawTextInput(
    val text: String,
    val significanceLevel: Double
) {
    init {
        if (significanceLevel <= 0.0 || significanceLevel >= 1.0) {
            throw BenfordInputValidationException("significanceLevel must be between 0 and 1")
        }
        if (text.isBlank()) {
            throw BenfordInputValidationException("text must not be blank")
        }
    }
}