package com.ricky.plugins

import com.ricky.routing.usuarioRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        usuarioRoutes()
    }
}