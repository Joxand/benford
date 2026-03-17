package com.johan.api.model

import kotlinx.serialization.Serializable

@Serializable
data class BenfordCheckRequest(
    val text: String,
    val significanceLevel: Double
)
