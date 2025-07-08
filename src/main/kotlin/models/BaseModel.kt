package com.ricky.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
abstract class BaseModel(
    var createdAt: LocalDateTime? = null,
    var createdBy: String? = null,
    var updatedAt: LocalDateTime? = null,
    var updatedBy: String? = null,
    var deleted: Boolean = false
)