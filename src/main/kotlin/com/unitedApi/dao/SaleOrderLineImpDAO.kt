package com.unitedApi.dao

import com.unitedApi.model.Customer
import com.unitedApi.model.SaleOrderLine
import com.unitedApi.model.mappingDB
import java.sql.Connection
import java.sql.Date

class SaleOrderLineImpDAO(val connection: Connection):SaleOrderLineDAO {
    override fun getSaleOrderLines(): List<SaleOrderLine> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM SaleOrderLine")
        return mappingDB(resultSet,::SaleOrderLine)
    }

    override fun createSaleOrderLine(saleOrderLine: SaleOrderLine) {
        val statement = connection.prepareStatement("insert into SaleOrderLine(soLineId,soHeaderId,dateDue,partID) values(?,?,?,?)")
        statement.setString(1,saleOrderLine.soLineId)
        statement.setString(2,saleOrderLine.soHeaderId)
        statement.setDate(3, saleOrderLine.dateDue)
        statement.setString(4,saleOrderLine.partId)
        statement.executeUpdate()
    }

    override fun getSaleOrderLine(headerId: String,lineId:String): SaleOrderLine {
        val statement = connection.prepareStatement("SELECT * FROM SaleOrderLine where soLineId = ? and soHeaderId = ?")
        statement.setString(1,lineId)
        statement.setString(2,headerId)
        val resultSet = statement.executeQuery()
        return mappingDB(resultSet,::SaleOrderLine)[0]
    }

    override fun getSaleOrderLinesByHeader(id: String): List<SaleOrderLine> {
        val statement = connection.prepareStatement("SELECT * FROM SaleOrderLine where soHeaderId = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        return mappingDB(resultSet,::SaleOrderLine)
    }
}