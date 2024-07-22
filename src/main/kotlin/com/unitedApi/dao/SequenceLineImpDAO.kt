package com.unitedApi.dao

import com.unitedApi.model.SaleOrderLine
import com.unitedApi.model.SequenceHeader
import com.unitedApi.model.SequenceLine
import java.sql.Connection

class SequenceLineImpDAO(val connection: Connection):SequenceLineDAO {
    override fun getSequencelines(): List<SequenceLine> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM SequenceLine")
        val lines = mutableListOf<SequenceLine>()
        while (resultSet.next())
        {
            lines.add(SequenceLine(resultSet.getString("seqLineId"),resultSet.getString("seqHeaderId"),resultSet.getString("workStation")))
        }
        return lines
    }

    override fun createSequenceLines(sequenceLine: SequenceLine) {
        val statement = connection.prepareStatement("insert into SequenceLine(seqLineId,seqHeaderId,workStation) values(?,?,?)")
        statement.setString(1,sequenceLine.seqLineId)
        statement.setString(2,sequenceLine.seqHeaderId)
        statement.setString(3, sequenceLine.workStation)
        statement.executeUpdate()
    }

    override fun getSequenceline(lineId:String,HeaderId:String): SequenceLine {
        val statement = connection.prepareStatement("SELECT * FROM SequenceLine where seqLineId = ? and seqHeaderId = ?")
        statement.setString(1,lineId)
        statement.setString(2,HeaderId)
        val resultSet = statement.executeQuery()
        resultSet.next()
        return SequenceLine(resultSet.getString("seqLineId"),resultSet.getString("seqHeaderId"),resultSet.getString("workStation"))
    }

    override fun getSequenceLineByHeader(id: String): List<SequenceLine> {
        val statement = connection.prepareStatement("SELECT * FROM SequenceLine where seqHeaderId = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        val lines = mutableListOf<SequenceLine>()
        while (resultSet.next())
        {
            lines.add(SequenceLine(resultSet.getString("seqLineId"),resultSet.getString("seqHeaderId"),resultSet.getString("workStation")))
        }
        return lines
    }

    override fun getSequenceLineByWork(id: String): List<SequenceLine> {
        val statement = connection.prepareStatement("SELECT * FROM SequenceLine where workStation = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        val lines = mutableListOf<SequenceLine>()
        while (resultSet.next())
        {
            lines.add(SequenceLine(resultSet.getString("seqLineId"),resultSet.getString("seqHeaderId"),resultSet.getString("workStation")))
        }
        return lines
    }
}