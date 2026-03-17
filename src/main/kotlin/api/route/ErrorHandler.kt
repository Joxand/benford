package com.johan.api.route

import com.johan.domain.service.BenfordInputValidationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("BenfordRoutes")

fun Application.configureErrorHandling() {

    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            logger.info("Bad request to ${call.request.httpMethod.value} - ${call.request.uri}", cause)
            call.respond(HttpStatusCode.BadRequest)
        }

        exception<BenfordInputValidationException> { call, cause ->
            logger.info("Input validation failed for ${call.request.httpMethod.value} - ${call.request.uri}", cause)
            call.respond(HttpStatusCode.UnprocessableEntity)
        }

        exception<Throwable> { call, cause ->
            logger.error(
                "Unhandled exception while processing request ${call.request.httpMethod.value} - ${call.request.uri}",
                cause
            )
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}