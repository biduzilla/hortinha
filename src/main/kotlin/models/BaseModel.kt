package com.ricky.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
abstract class BaseModel(
    var createdAt: LocalDateTime? = null,
    var createdBy: String? = null,
    var updatedAt: LocalDateTime? = null,
    var updatedBy: String? = null,
    var deleted: Boolean = false
)