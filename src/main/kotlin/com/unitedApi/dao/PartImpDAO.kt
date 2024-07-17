package com.unitedApi.dao

import com.unitedApi.model.Customer
import com.unitedApi.model.Part
import java.sql.Connection

class PartImpDAO(val connection: Connection):PartDAO {
    override fun getParts(): List<Part> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM Part")
        val parts = mutableListOf<Part>()
        while(resultSet.next())
        {
            parts.add(Part(resultSet.getString("partId"),resultSet.getInt("inventory"),resultSet.getString("name")))
        }
        return parts
    }

    override fun createPart(part: Part) {
        val statement = connection.prepareStatement("INSERT into Part(partId,inventory,name) values(?,?,?)")
        statement.setString(1,part.PartId)
        statement.setInt(2,part.inventory)
        statement.setString(3,part.name)
        statement.executeUpdate()
    }

    override fun getPart(id: String): Part {
        val statement = connection.prepareStatement("SELECT * From Part where partId = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        resultSet.next()
        return Part(resultSet.getString("partId"),resultSet.getInt("inventory"),resultSet.getString("name"))
    }
}