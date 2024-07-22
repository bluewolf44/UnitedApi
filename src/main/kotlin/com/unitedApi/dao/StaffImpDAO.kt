package com.unitedApi.dao

import com.unitedApi.model.SequenceHeader
import com.unitedApi.model.Staff
import java.sql.Connection

class StaffImpDAO(val connection: Connection):StaffDAO {
    override fun getStaffs(): List<Staff> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM Staff")
        val staffs = mutableListOf<Staff>()
        while (resultSet.next())
        {
            staffs.add(Staff(resultSet.getString("staffId"),resultSet.getString("name")))
        }
        return staffs
    }

    override fun createStaff(staff: Staff) {
        val statement = connection.prepareStatement("insert into Staff(staffId,name) values(?,?)")
        statement.setString(1,staff.staffId)
        statement.setString(2,staff.name)
        statement.executeUpdate()
    }

    override fun getStaff(id:String): Staff {
        val statement = connection.prepareStatement("SELECT * FROM Staff where staffId = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        resultSet.next()
        return Staff(resultSet.getString("staffId"),resultSet.getString("name"))
    }
}