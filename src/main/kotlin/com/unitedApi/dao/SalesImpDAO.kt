package com.unitedApi.dao

import com.unitedApi.model.Sales
import kotlinx.coroutines.runBlocking
import java.sql.Connection
import java.sql.DriverManager

class SalesImpDAO:SalesDAO{
    override suspend fun getSales(): List<String> {
        val dbConnection: Connection = DriverManager.getConnection(
            "jdbc:postgresql://db:5432/ktorjournal",
            "postgres","postgres"
        )
        val statement = dbConnection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM sales")
        val sales = mutableListOf<String>()
        while (resultSet.next())
        {
            sales.add(resultSet.getString("customer"))
        }
        return sales

    }

}

val salesDao: SalesDAO = SalesImpDAO().apply {
    runBlocking {
    }
}