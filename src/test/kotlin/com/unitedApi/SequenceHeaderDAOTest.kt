package com.unitedApi

import com.unitedApi.dao.PartDAO
import com.unitedApi.dao.PartImpDAO
import com.unitedApi.dao.SequenceHeaderDAO
import com.unitedApi.dao.SequenceHeaderImpDAO
import com.unitedApi.model.Part
import com.unitedApi.model.SequenceHeader
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.sql.Connection
import java.sql.DriverManager

class SequenceHeaderDAOTest {

    lateinit var partDAO: PartDAO
    lateinit var sequenceHeaderDAO: SequenceHeaderDAO
    lateinit var dbConnection: Connection

    val part = Part("1", 50, "Cheese Balls")
    val sequenceHeader = SequenceHeader("1",part.PartId)

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
        partDAO = PartImpDAO(dbConnection).apply { runBlocking {} }
        sequenceHeaderDAO = SequenceHeaderImpDAO(dbConnection).apply { runBlocking {  } }

        partDAO.createPart(part)
        sequenceHeaderDAO.createSequenceHeader(sequenceHeader)
    }

    @Test
    fun getSequenceHeaders() {
        assertEquals(sequenceHeaderDAO.getSequenceHeaders(), listOf(sequenceHeader))
    }

    @Test
    fun getSequenceHeader() {
        assertEquals(sequenceHeaderDAO.getSequenceHeader(sequenceHeader.seqHeaderId),sequenceHeader)
    }
}