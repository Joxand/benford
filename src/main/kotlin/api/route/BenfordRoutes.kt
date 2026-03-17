package com.johan.api.route

import com.johan.api.model.BenfordCheckRequest
import com.johan.api.model.BenfordCheckResponse
import com.johan.domain.model.RawTextInput
import com.johan.domain.model.BenfordCheckResult
import com.johan.domain.service.BenfordService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRoutes(benfordService: BenfordService) {
    routing {
        post("/api/v1/benford/check") {
            val request = call.receive<BenfordCheckRequest>()
            val result = benfordService.check(request.toDomain())
            call.respond(HttpStatusCode.OK, result.toDto())
        }
    }
}

private fun BenfordCheckResult.toDto(): BenfordCheckResponse =
    BenfordCheckResponse(
        expectedDistribution = expectedDistribution,
        observedDistribution = observedDistribution,
        followsBenford = followsBenford
    )

private fun BenfordCheckRequest.toDomain(): RawTextInput =
    RawTextInput(
        significanceLevel = significanceLevel,
        text = text
    )
