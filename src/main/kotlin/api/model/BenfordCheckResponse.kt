package com.johan.api.model

import kotlinx.serialization.Serializable

@Serializable
data class BenfordCheckResponse(
    val observedDistribution: Map<Int, Long>,
    val expectedDistribution: Map<Int, Double>,
    val followsBenford: Boolean,
)