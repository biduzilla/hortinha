package com.ricky.hortinha.exceptions

import com.ricky.hortinha.dto.ErrorView
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(GenericException::class)
    fun handleGenericException(
        exception: GenericException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorView> {
        return ResponseEntity.status(exception.httpStatus).body(
            ErrorView(
                status = exception.httpStatus.value(),
                error = exception.httpStatus.name,
                message = exception.message,
                path = request.servletPath
            )
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationError(
        exception: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ErrorView {
        val errorMessage = HashMap<String, String?>()
        exception.bindingResult.fieldErrors.forEach { e ->
            errorMessage[e.field] = e.defaultMessage
        }
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = errorMessage.toString(),
            path = request.servletPath
        )
    }
}