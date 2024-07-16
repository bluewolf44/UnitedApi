package com.unitedApi.dao

import com.unitedApi.model.Customer
import kotlinx.coroutines.runBlocking

class CustomerImpDAO:CustomerDAO{
    override suspend fun getCustomers(): List<Customer> {
        val statement = dbConnection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM customer")
        val customers = mutableListOf<Customer>()
//        while (resultSet.next())
//        {
//            customers.add(resultSet.getString("customer"))
//        }
        return customers

    }
}