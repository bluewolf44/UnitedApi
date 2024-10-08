package com.unitedApi.dao

import com.unitedApi.model.SaleOrderHeader
import com.unitedApi.model.WorkOrder
import com.unitedApi.model.WorkedOn
import com.unitedApi.model.mappingDB
import java.sql.Connection

class WorkedOnImpDAO(val connection: Connection):WorkedOnDAO {
    override fun getWorkedOns(): List<WorkedOn> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM WorkedOn")
        return mappingDB(resultSet, ::WorkedOn)
    }

    override fun createWorkedOn(workedOn: WorkedOn) {
        val  statement = connection.prepareStatement("INSERT into WorkedOn(workOrderId,staffId,seqLineId,seqHeaderId) values(?,?,?,?)")
        statement.setString(1,workedOn.workOrderId)
        statement.setString(2,workedOn.staffId)
        statement.setString(3,workedOn.seqLineId)
        statement.setString(4,workedOn.seqHeaderId)
        statement.executeUpdate()
    }

    override fun getWorkedOn(staffId: String,seqHeaderId:String, seqLineId: String, workOrderId: String): WorkedOn {
        val statement = connection.prepareStatement("SELECT * FROM WorkedOn where staffId = ? and seqHeaderId = ? and seqLineId = ? and workOrderId = ?")
        statement.setString(1,staffId)
        statement.setString(2,seqHeaderId)
        statement.setString(3,seqLineId)
        statement.setString(4,workOrderId)
        val resultSet = statement.executeQuery()
        return mappingDB(resultSet, ::WorkedOn)[0]
    }

    override fun getWorkedOnByStaff(staffId: String): List<WorkedOn> {
        val statement = connection.prepareStatement("SELECT * FROM WorkedOn where staffId = ?")
        statement.setString(1,staffId)
        val resultSet = statement.executeQuery()
        return mappingDB(resultSet, ::WorkedOn)
    }

    override fun getWorkedOnByWorkOrder(workOrderId: String): List<WorkedOn> {
        val statement = connection.prepareStatement("SELECT * FROM WorkedOn where workOrderId = ?")
        statement.setString(1,workOrderId)
        val resultSet = statement.executeQuery()
        return mappingDB(resultSet, ::WorkedOn)
    }
}