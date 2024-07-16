package com.unitedApi.routing

import com.unitedApi.dao.salesDao
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        //Chnage again
        get("/") {
            call.respondText("Hello World!")
            //call.respondText("Cheese")
        }
        CustomerRounting()
    }
}
