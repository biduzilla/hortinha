package com.ricky.hortinha.utils

import com.ricky.hortinha.exceptions.GenericException
import kotlin.random.Random

fun geradorCodigo(): Int {
    val random = Random(System.currentTimeMillis())
    return random.nextInt(100000, 1000000)
}
