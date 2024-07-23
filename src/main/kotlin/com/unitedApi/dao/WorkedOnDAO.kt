package com.unitedApi.dao

import com.unitedApi.model.WorkedOn

interface WorkedOnDAO {
    fun getWorkedOns():List<WorkedOn>
    fun createWorkedOn(workedOn:WorkedOn)
    fun getWorkedOn(staffId:String,seqHeaderId:String,seqLineId:String,workOrderId:String):WorkedOn
    fun getWorkedOnByStaff(staffId: String):List<WorkedOn>
    fun getWorkedOnByWorkOrder(workOrderId: String):List<WorkedOn>
}