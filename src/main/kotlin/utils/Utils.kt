package com.ricky.utils

import com.ricky.exceptions.GenericServerError
import com.ricky.models.Page
import io.ktor.http.*
import io.ktor.server.routing.*
import java.util.*

fun getOffset(page: Int, pageSize: Int): Long = ((page - 1) * pageSize).toLong()
fun getLastPage(totalRecords: Long, pageSize: Int): Int {
    if (totalRecords <= 0 || pageSize <= 0) return 0
    return ((totalRecords - 1) / pageSize).toInt() + 1
}

fun RoutingCall.getValidatedPage(): Int {
    return queryParameters["page"]
        ?.toIntOrNull()
        ?.takeIf { it > -1 }
        ?: 0
}

fun RoutingCall.getValidatedPageSize(): Int {
    return queryParameters["pageSize"]
        ?.toIntOrNull()
        ?.takeIf { it in 1..100 }
        ?: 10
}

fun RoutingCall.getIdUUID(): UUID {
    val idString = this.parameters["id"]
        ?: throw GenericServerError(
            statusCode = HttpStatusCode.BadRequest.value,
            errorMessage = "ID é obrigatório",
            httpStatus = HttpStatusCode.BadRequest.description
        )
    return try {
        UUID.fromString(idString)
    } catch (e: IllegalArgumentException) {
        throw GenericServerError(
            statusCode = HttpStatusCode.BadRequest.value,
            errorMessage = "ID inválido. Formato esperado: UUID",
            httpStatus = HttpStatusCode.BadRequest.description
        )
    }
}

inline fun <T, R> Page<T>.mapContent(transform: (T) -> R): Page<R> {
    return Page(
        currentPage = this.currentPage,
        pageSize = this.pageSize,
        firstPage = this.firstPage,
        lastPage = this.lastPage,
        totalRecords = this.totalRecords,
        content = this.content.map(transform)
    )
}