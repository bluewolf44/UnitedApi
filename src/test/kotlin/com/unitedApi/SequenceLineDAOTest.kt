package com.unitedApi

import com.unitedApi.dao.*
import com.unitedApi.model.Part
import com.unitedApi.model.SequenceHeader
import com.unitedApi.model.SequenceLine
import com.unitedApi.model.WorkStation
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.sql.Connection
import java.sql.DriverManager
import kotlin.test.assertEquals

class SequenceLineDAOTest {

    lateinit var partDAO: PartDAO
    lateinit var sequenceHeaderDAO: SequenceHeaderDAO
    lateinit var sequenceLineDAO: SequenceLineDAO
    lateinit var workStationDAO: WorkStationDAO
    lateinit var dbConnection: Connection

    val part = Part("1", 50, "Cheese Balls")
    val sequenceHeader1 = SequenceHeader("1",part.PartId)
    val sequenceHeader2 = SequenceHeader("2",part.PartId)
    val workStation1 = WorkStation("Cheese Chaser")
    val workStation2 = WorkStation("Leaf Eater")
    val sequenceLine1 = SequenceLine("1",sequenceHeader1.seqHeaderId,workStation1.name)
    val sequenceLine2 = SequenceLine("2",sequenceHeader1.seqHeaderId,workStation2.name)
    val sequenceLine3 = SequenceLine("1",sequenceHeader2.seqHeaderId,workStation1.name)

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
        sequenceLineDAO = SequenceLineImpDAO(dbConnection)
        sequenceHeaderDAO = SequenceHeaderImpDAO(dbConnection)
        workStationDAO = WorkStationImpDAO(dbConnection)

        partDAO.createPart(part)
        sequenceHeaderDAO.createSequenceHeader(sequenceHeader1)
        sequenceHeaderDAO.createSequenceHeader(sequenceHeader2)

        workStationDAO.createWorkStation(workStation1)
        workStationDAO.createWorkStation(workStation2)

        sequenceLineDAO.createSequenceLines(sequenceLine1)
        sequenceLineDAO.createSequenceLines(sequenceLine2)
        sequenceLineDAO.createSequenceLines(sequenceLine3)
    }

    @Test
    fun getSequencelines() {
        assertEquals(sequenceLineDAO.getSequencelines(),listOf(sequenceLine1,sequenceLine2,sequenceLine3))
    }

    @Test
    fun getSequenceline() {
        assertEquals(sequenceLineDAO.getSequenceline(sequenceLine1.seqLineId,sequenceLine1.seqHeaderId),sequenceLine1)
    }

    @Test
    fun getSequenceLineByHeader() {
        assertEquals(sequenceLineDAO.getSequenceLineByHeader(sequenceHeader1.seqHeaderId), listOf(sequenceLine1,sequenceLine2))
    }

    @Test
    fun getSequenceLineByWork() {
        assertEquals(sequenceLineDAO.getSequenceLineByWork(workStation1.name), listOf(sequenceLine1,sequenceLine3))
    }
}