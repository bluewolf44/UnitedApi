package com.unitedApi

import com.unitedApi.dao.PartDAO
import com.unitedApi.dao.PartImpDAO
import com.unitedApi.model.Part
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.sql.Connection
import java.sql.DriverManager
import kotlin.test.assertContains
import kotlin.test.assertEquals


class PartDAOTest {

    lateinit var partDAO: PartDAO
    lateinit var dbConnection: Connection

    val part = Part("1", 50, "Cheese Balls")

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
        partDAO = PartImpDAO(dbConnection)

        partDAO.createPart(part)
    }

    @Test
    fun getParts()
    {
        assertContains(partDAO.getParts(),part)
    }

    @Test
    fun getPart()
    {
        assertEquals(partDAO.getPart(part.partId),part)
    }

}