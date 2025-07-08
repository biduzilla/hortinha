package com.ricky.utils

fun getOffset(page: Int, pageSize: Int): Long = ((page - 1) * pageSize).toLong()
fun getLastPage(totalRecords: Long, pageSize: Int): Int {
    if (totalRecords <= 0 || pageSize <= 0) return 0
    return ((totalRecords - 1) / pageSize).toInt() + 1
}