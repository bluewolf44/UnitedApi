package com.unitedApi

import com.unitedApi.plugins.configureHTTP
import com.unitedApi.plugins.configureRouting
import com.unitedApi.plugins.configureSerialization
import io.ktor.server.application.*
import java.sql.Connection
import java.sql.DriverManager

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureSerialization()
    configureRouting()
}


