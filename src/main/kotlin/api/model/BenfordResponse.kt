package com.johan.api.model

import kotlinx.serialization.Serializable

@Serializable
data class BenfordResponse(
    val significanceLevel: Double,
    val followsBenford: Boolean,
)