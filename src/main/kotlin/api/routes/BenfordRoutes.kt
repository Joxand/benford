package com.johan.api.routes

import com.johan.api.model.BenfordRequest
import com.johan.api.model.BenfordResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.response.*


fun Application.configureBenfordRoutes() {

    routing {
        post("/api/v1/benford/check") {
            val request = call.receive<BenfordRequest>()

            val response = BenfordResponse(request.significanceLevel, true)

            call.respond(HttpStatusCode.OK, response)
        }
    }
}
