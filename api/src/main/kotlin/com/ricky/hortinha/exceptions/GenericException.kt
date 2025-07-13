package com.ricky.hortinha.exceptions

import org.springframework.http.HttpStatus

class GenericException(
    val msg: String,
    val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST
) : RuntimeException(msg) {
}