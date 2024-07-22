package com.unitedApi

import com.unitedApi.dao.WorkStationDAO
import com.unitedApi.dao.WorkStationImpDAO
import com.unitedApi.model.WorkStation
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.sql.Connection
import java.sql.DriverManager

class WorkStationDAOTest {

    lateinit var workStationDAO:WorkStationDAO
    lateinit var dbConnection: Connection

    val workStation = WorkStation("Cheese Chaser")

    @JvmField
    @Rule
    val postgreContainer = PostgreSQLContainer(DockerImageName.parse("postgres:latest")).apply {
        withDatabaseName("db")
        withUsername("user")
        withPassword("password")
        withInitScript("sql/init.sql")
        //withExposedPorts(5432)
        start()
    }

    @Before
    fun setUp() {
        dbConnection = DriverManager.getConnection(
            "jdbc:postgresql://" + postgreContainer.getHost() + ":" + postgreContainer.getFirstMappedPort() + "/" + postgreContainer.getDatabaseName(),
            postgreContainer.getUsername(), postgreContainer.getPassword()
        )

        workStationDAO = WorkStationImpDAO(dbConnection)
        workStationDAO.createWorkStation(workStation)
    }

    @Test
    fun getWorkStations() {
        assertEquals(workStationDAO.getWorkStations(), listOf(workStation))
    }

    @Test
    fun getWorkStation() {
        assertEquals(workStationDAO.getWorkStation(workStation.name), workStation)
    }
}