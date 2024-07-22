package com.unitedApi.dao

import com.unitedApi.model.Staff

interface StaffDAO {
    fun getStaffs():List<Staff>
    fun createStaff(staff:Staff)
    fun getStaff(id:String):Staff
}