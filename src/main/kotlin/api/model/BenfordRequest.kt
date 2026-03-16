package com.johan.api.model

import kotlinx.serialization.Serializable

@Serializable
data class BenfordRequest(
    val input: String,
    val significanceLevel: Double
)
