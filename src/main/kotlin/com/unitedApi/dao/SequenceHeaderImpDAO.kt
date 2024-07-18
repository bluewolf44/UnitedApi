package com.unitedApi.dao

import com.unitedApi.model.SaleOrderLine
import com.unitedApi.model.SequenceHeader
import java.sql.Connection

class SequenceHeaderImpDAO(val connection:Connection):SequenceHeaderDAO {

    override fun getSequenceHeaders(): List<SequenceHeader> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM SequenceHeader")
        val headers = mutableListOf<SequenceHeader>()
        while (resultSet.next())
        {
            headers.add(SequenceHeader(resultSet.getString("seqHeaderId"),resultSet.getString("partId")))
        }
        return headers
    }

    override fun createSequenceHeader(sequenceHeader: SequenceHeader) {
        val statement = connection.prepareStatement("insert into SequenceHeader(seqHeaderId,partId) values(?,?)")
        statement.setString(1,sequenceHeader.seqHeaderId)
        statement.setString(2,sequenceHeader.partId)
        statement.executeUpdate()
    }

    override fun getSequenceHeader(id: String): SequenceHeader {
        val statement = connection.prepareStatement("SELECT * FROM SequenceHeader where seqHeaderId = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        resultSet.next()
        return SequenceHeader(resultSet.getString("seqHeaderId"),resultSet.getString("partId"))
    }
}