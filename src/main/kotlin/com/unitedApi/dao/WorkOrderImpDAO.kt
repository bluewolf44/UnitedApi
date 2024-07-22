package com.unitedApi.dao

import com.unitedApi.model.SequenceHeader
import com.unitedApi.model.WorkOrder
import java.sql.Connection

class WorkOrderImpDAO(val connection: Connection):WorkOrderDAO {
    override fun getWorkOrders(): List<WorkOrder> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM WorkOrder")
        val orders = mutableListOf<WorkOrder>()
        while (resultSet.next())
        {
            orders.add(WorkOrder(resultSet.getString("workOrderId"),resultSet.getString("seqHeaderId"),resultSet.getString("soHeaderID"),resultSet.getString("soLineId"),resultSet.getInt("quanity")))
        }
        return orders
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
        resultSet.next()
        return WorkOrder(resultSet.getString("workOrderId"),resultSet.getString("seqHeaderId"),resultSet.getString("soHeaderID"),resultSet.getString("soLineId"),resultSet.getInt("quanity"))
    }

    override fun getWorkOrderBySaleLine(headerId: String,lineId:String): List<WorkOrder> {
        val statement = connection.prepareStatement("SELECT * FROM WorkOrder where soHeaderID = ? and soLineId = ?")
        statement.setString(1,headerId)
        statement.setString(2,lineId)
        val resultSet = statement.executeQuery()
        val orders = mutableListOf<WorkOrder>()
        while (resultSet.next())
        {
            orders.add(WorkOrder(resultSet.getString("workOrderId"),resultSet.getString("seqHeaderId"),resultSet.getString("soHeaderID"),resultSet.getString("soLineId"),resultSet.getInt("quanity")))
        }
        return orders
    }
}