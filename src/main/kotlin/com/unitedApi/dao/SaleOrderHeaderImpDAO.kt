package com.unitedApi.dao

import com.unitedApi.model.Part
import com.unitedApi.model.SaleOrderHeader
import io.ktor.network.sockets.*
import java.sql.Connection

class SaleOrderHeaderImpDAO(val connection:Connection):SaleOrderHeaderDAO {

    override fun getSaleOrderHeaders(): List<SaleOrderHeader> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM SaleOrderHeader")
        val saleOrderHeaders = mutableListOf<SaleOrderHeader>()
        while(resultSet.next())
        {
            saleOrderHeaders.add(SaleOrderHeader(resultSet.getString("soHeaderId"),resultSet.getString("customerId")))
        }
        return saleOrderHeaders
    }

    override fun createSaleOrderHeader(saleOrderHeader: SaleOrderHeader) {
        val  statement = connection.prepareStatement("INSERT into SaleOrderHeader(soHeaderID,customerID) values(?,?)")
        statement.setString(1,saleOrderHeader.seqHeaderId)
        statement.setString(2,saleOrderHeader.customerId)
        statement.executeUpdate()
    }

    override fun getSaleOrderHeader(id: String): SaleOrderHeader {
        val statement = connection.prepareStatement("SELECT * FROM SaleOrderHeader where soHeaderID = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        resultSet.next()
        return SaleOrderHeader(resultSet.getString("soHeaderId"),resultSet.getString("customerId"))
    }

    override fun getSaleOrderHeaderByCustomerId(id: String): List<SaleOrderHeader> {
        val statement = connection.prepareStatement("SELECT * FROM SaleOrderHeader where customerId = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        val saleOrderHeaders = mutableListOf<SaleOrderHeader>()
        while(resultSet.next())
        {
            saleOrderHeaders.add(SaleOrderHeader(resultSet.getString("soHeaderId"),resultSet.getString("customerId")))
        }
        return saleOrderHeaders
    }
}

