package com.ricky.models

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable


@Serializable
abstract class BaseModel(
    var createdAt: LocalDateTime? = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    var createdBy: String? = null,
    var updatedAt: LocalDateTime? = null,
    var updatedBy: String? = null,
    var deleted: Boolean = false
)