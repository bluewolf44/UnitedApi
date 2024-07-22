package com.unitedApi

import com.unitedApi.dao.StaffDAO
import com.unitedApi.dao.StaffImpDAO
import com.unitedApi.model.Staff
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.sql.Connection
import java.sql.DriverManager

class StaffDAOTest {

    lateinit var staffDAO: StaffDAO
    lateinit var dbConnection: Connection

    val staff = Staff("1","James")

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
        staffDAO = StaffImpDAO(dbConnection)

        staffDAO.createStaff(staff)
    }

    @Test
    fun getStaffs() {
        assertEquals(staffDAO.getStaffs(), listOf(staff))
    }

    @Test
    fun getStaff() {
        assertEquals(staffDAO.getStaff(staff.staffId),staff)
    }
}