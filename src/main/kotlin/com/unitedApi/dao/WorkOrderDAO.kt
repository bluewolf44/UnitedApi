package com.unitedApi.dao

import com.unitedApi.model.WorkOrder

interface WorkOrderDAO {
    fun getWorkOrders(): List<WorkOrder>
    fun createWorkOrder(workOrder: WorkOrder)
    fun getWorkOrder(id:String): WorkOrder
    fun getWorkOrderBySaleLine(headerId: String,lineId:String):List<WorkOrder>
}