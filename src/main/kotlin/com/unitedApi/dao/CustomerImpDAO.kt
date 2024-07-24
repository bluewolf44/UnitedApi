package com.unitedApi.dao

import com.unitedApi.model.Customer
import com.unitedApi.model.mappingDB
import java.sql.Connection

class CustomerImpDAO(val connection: Connection):CustomerDAO{

    override fun getCustomers(): List<Customer> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM Customer")
        return mappingDB(resultSet,::Customer)
    }

    override fun createCustomer(customer: Customer) {
        val statement = connection.prepareStatement("insert into Customer(customerId,name,address) values(?,?,?)")
        statement.setString(1,customer.customerId)
        statement.setString(2,customer.name)
        statement.setString(3,customer.address)
        statement.executeUpdate()
    }

    override fun getCustomer(id: String): Customer {
        val statement = connection.prepareStatement("SELECT * FROM Customer where customerId = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        return mappingDB(resultSet,::Customer)[0]
    }
}