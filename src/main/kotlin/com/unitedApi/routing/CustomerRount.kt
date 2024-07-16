package com.unitedApi.routing

import com.unitedApi.dao.customerDAO
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.CustomerRounting()
{
    route("customer")
    {
        get("/") {
            call.respond(customerDAO.getCustomers())
        }
    }
}
