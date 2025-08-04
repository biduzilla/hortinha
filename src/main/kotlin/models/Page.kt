package com.ricky.models

import kotlinx.serialization.Serializable

@Serializable
data class Page<T>(
    var currentPage: Int = 0,
    var pageSize: Int = 10,
    var firstPage: Int = 0,
    var lastPage: Int = 0,
    var totalRecords: Int = 0,
    var content: List<T> = emptyList()
)
