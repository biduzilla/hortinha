package com.ricky.hortinha.utils

import kotlin.random.Random

fun geradorCodigo(): Int {
    val random = Random(System.currentTimeMillis())
    return random.nextInt(100000, 1000000)
}
