package com.unitedApi.dao

import com.unitedApi.model.SequenceHeader
import com.unitedApi.model.WorkStation
import com.unitedApi.model.mappingDB
import java.sql.Connection

class WorkStationImpDAO(val connection: Connection):WorkStationDAO {
    override fun getWorkStations(): List<WorkStation> {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM WorkStation")
        return mappingDB(resultSet, ::WorkStation)
    }

    override fun createWorkStation(workStation: WorkStation) {
        val statement = connection.prepareStatement("insert into WorkStation(name) values(?)")
        statement.setString(1,workStation.name)
        statement.executeUpdate()
    }

    override fun getWorkStation(id: String): WorkStation {
        val statement = connection.prepareStatement("SELECT * FROM WorkStation where name = ?")
        statement.setString(1,id)
        val resultSet = statement.executeQuery()
        return mappingDB(resultSet, ::WorkStation)[0]
    }
}