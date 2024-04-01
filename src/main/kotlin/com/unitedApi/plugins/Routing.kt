package com.unitedApi.plugins

import com.unitedApi.dao.SalesDAO
import com.unitedApi.dao.salesDao
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/Test") {
            call.respond(salesDao.getSales())
        }
    }
}
