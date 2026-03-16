package com.johan

import com.johan.api.routes.configureBenfordRoutes
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureBenfordRoutes()
}
