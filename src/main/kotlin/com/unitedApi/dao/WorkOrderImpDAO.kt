package com.unitedApi.dao

import com.unitedApi.model.SequenceHeader
import com.unitedApi.model.WorkOrder
import com.unitedApi.model.mappingDB
import java.sql.Connection

class WorkOrderImpDAO(val connection: Connection):WorkOrderDAO {
    override fun getWorkOrders(): List<WorkOrder> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM WorkOrder")
        return mappingDB(resultSet, ::WorkOrder)
    }

    override fun createWorkOrder(workOrder: WorkOrder) {
        val statement = connection.prepareStatement("insert into WorkOrder(workOrderId,seqHeaderId,soHeaderID,soLineId,quanity) values(?,?,?,?,?)")
        statement.setString(1,workOrder.workOrderId)
        statement.setString(2,workOrder.seqHeaderId)
        statement.setString(3,workOrder.soHeaderID)
        statement.setString(4,workOrder.soLineId)
        statement.setInt(5,workOrder.quanity)
        statement.executeUpdate()
    }

    override fun getWorkOrder(id: String): WorkOrder {
        val statement = connection.prepareStatement("SELECT * FROM WorkOrder where workOrderId = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        return mappingDB(resultSet, ::WorkOrder)[0]
    }

    override fun getWorkOrderBySaleLine(headerId: String,lineId:String): List<WorkOrder> {
        val statement = connection.prepareStatement("SELECT * FROM WorkOrder where soHeaderID = ? and soLineId = ?")
        statement.setString(1,headerId)
        statement.setString(2,lineId)
        val resultSet = statement.executeQuery()
        return mappingDB(resultSet, ::WorkOrder)
    }
}