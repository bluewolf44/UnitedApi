package com.unitedApi.dao

import com.unitedApi.model.SaleOrderLine
import com.unitedApi.model.SequenceHeader
import com.unitedApi.model.mappingDB
import java.sql.Connection

class SequenceHeaderImpDAO(val connection:Connection):SequenceHeaderDAO {

    override fun getSequenceHeaders(): List<SequenceHeader> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM SequenceHeader")
        return mappingDB(resultSet,::SequenceHeader)
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
        return mappingDB(resultSet,::SequenceHeader)[0]
    }
}