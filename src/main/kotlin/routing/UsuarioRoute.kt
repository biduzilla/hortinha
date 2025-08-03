package com.ricky.routing

import com.ricky.dto.UsuarioSaveDTO
import com.ricky.service.UsuarioService
import com.ricky.utils.getIdUUID
import com.ricky.utils.getValidatedPage
import com.ricky.utils.getValidatedPageSize
import com.ricky.utils.mapContent
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.usuarioRoutes(usuarioService: UsuarioService) {

    route("/usuario") {
        get {
            val pageSize = call.getValidatedPageSize()

            val page = call.getValidatedPage()
            val users = usuarioService.getAll(pageSize, page).mapContent { it.toDTO() }
            call.respond(users)
        }
        get("/{id}") {
            val id = call.getIdUUID()
            val user = usuarioService.getById(id)
            call.respond(user.toDTO())
        }
        post {
            val user = call.receive<UsuarioSaveDTO>()
            val userSave = usuarioService.save(user.toModel())
            call.respond(HttpStatusCode.Created, userSave)
        }
        delete("/{id}") {
            val id = call.getIdUUID()
            usuarioService.deleteById(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}