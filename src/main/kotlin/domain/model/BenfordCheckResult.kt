package com.johan.domain.model

data class BenfordCheckResult(
    val observedDistribution: Map<Int, Long>,
    val expectedDistribution: Map<Int, Double>,
    val followsBenford: Boolean,
)
