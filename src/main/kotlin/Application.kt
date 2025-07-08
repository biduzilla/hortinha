package com.ricky

import com.ricky.plugins.configureRouting
import com.ricky.plugins.configureSerialization
import com.ricky.plugins.configureStatusPages
import com.ricky.plugins.configureValidation
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureStatusPages()
    configureValidation()
    configureRouting()
}
