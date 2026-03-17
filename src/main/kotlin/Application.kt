package com.johan

import com.johan.api.route.configureErrorHandling
import com.johan.api.route.configureRoutes
import com.johan.domain.service.BenfordService
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureErrorHandling()
    configureSerialization()
    configureRoutes(BenfordService())
}
