package com.ricky.plugins

import com.ricky.exceptions.GenericServerError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is GenericServerError -> {
                    call.respond(HttpStatusCode.fromValue(cause.statusCode), cause)
                }

                is RequestValidationException -> {
                    call.respond(HttpStatusCode.BadRequest, GenericServerError(
                        statusCode = HttpStatusCode.BadRequest.value,
                        errorMessage = cause.reasons.toString(),
                        httpStatus = HttpStatusCode.BadRequest.description
                    ))
                }

                else -> {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("message" to "Erro interno no servidor")
                    )
                    throw cause
                }
            }
        }
    }
}