package com.unitedApi.dao

import com.unitedApi.model.Customer
import com.unitedApi.model.SaleOrderLine
import java.sql.Connection
import java.sql.Date

class SaleOrderLineImpDAO(val connection: Connection):SaleOrderLineDAO {
    override fun getSaleOrderLines(): List<SaleOrderLine> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM SaleOrderLine")
        val lines = mutableListOf<SaleOrderLine>()
        while (resultSet.next())
        {
            lines.add(SaleOrderLine(resultSet.getString("soLineId"),resultSet.getString("soHeaderId"),resultSet.getDate("dateDue"),resultSet.getString("partID")))
        }
        return lines
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
        resultSet.next()
        return SaleOrderLine(resultSet.getString("soLineId"),resultSet.getString("soHeaderId"),resultSet.getDate("dateDue"),resultSet.getString("partID"))
    }

    override fun getSaleOrderLinesByHeader(id: String): List<SaleOrderLine> {
        val statement = connection.prepareStatement("SELECT * FROM SaleOrderLine where soHeaderId = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        val lines = mutableListOf<SaleOrderLine>()
        while (resultSet.next())
        {
            lines.add(SaleOrderLine(resultSet.getString("soLineId"),resultSet.getString("soHeaderId"),resultSet.getDate("dateDue"),resultSet.getString("partID")))
        }
        return lines
    }
}