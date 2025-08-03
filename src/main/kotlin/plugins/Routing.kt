package com.ricky.plugins

import com.ricky.routing.usuarioRoutes
import com.ricky.service.UsuarioService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val usuarioService by inject<UsuarioService>()

    routing {
        usuarioRoutes(usuarioService)
    }
}