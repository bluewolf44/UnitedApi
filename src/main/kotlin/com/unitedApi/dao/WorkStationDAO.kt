package com.unitedApi.dao

import com.unitedApi.model.WorkStation

interface WorkStationDAO {
    fun getWorkStations():List<WorkStation>
    fun createWorkStation(workStation:WorkStation)
    fun getWorkStation(id:String):WorkStation
}