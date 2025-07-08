package com.ricky.exceptions

import kotlinx.serialization.Serializable

@Serializable
data class GenericServerError(
    val statusCode: Int,
    val errorMessage: String,
    val httpStatus: String? = null
) : RuntimeException(errorMessage)