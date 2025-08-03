package com.ricky

import com.ricky.module.appModule
import com.ricky.plugins.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    configureDatabases()
    configureSerialization()
    configureStatusPages()
    configureValidation()
    configureRouting()
}
