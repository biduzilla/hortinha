package com.ricky.hortinha.exceptions

import org.springframework.http.HttpStatus

class GenericException(msg: String, httpStatus: HttpStatus = HttpStatus.BAD_REQUEST) : RuntimeException(msg) {
}