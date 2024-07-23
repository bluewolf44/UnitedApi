package com.unitedApi

import com.unitedApi.dao.*
import com.unitedApi.model.*
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager
import java.time.LocalDate

class WorkedOnDAOTest {


    lateinit var saleOrderHeaderDAO: SaleOrderHeaderDAO
    lateinit var customerDAO: CustomerImpDAO
    lateinit var saleOrderLineDAO: SaleOrderLineDAO
    lateinit var partDAO: PartDAO
    lateinit var sequenceHeaderDAO: SequenceHeaderDAO
    lateinit var sequenceLineDAO: SequenceLineDAO
    lateinit var workOrderDAO: WorkOrderDAO
    lateinit var staffDAO: StaffDAO
    lateinit var workedOnDAO: WorkedOnDAO
    lateinit var workStationDAO: WorkStationDAO
    lateinit var dbConnection: Connection

    val part = Part("1", 50, "Cheese Balls")
    val sequenceHeader = SequenceHeader("1",part.PartId)
    val workStation1 = WorkStation("Cheese Chaser")
    val workStation2 = WorkStation("Leaf Fighter")
    val sequenceLine1 = SequenceLine("1",sequenceHeader.seqHeaderId,workStation1.name)
    val sequenceLine2 = SequenceLine("2",sequenceHeader.seqHeaderId,workStation2.name)
    val customer = Customer("1","Dave","69th street")
    val saleOrderHeader = SaleOrderHeader("1",customer.customerId)
    val saleOrderLine1 = SaleOrderLine("1",saleOrderHeader.soHeaderId, Date.valueOf(LocalDate.now()),part.PartId)
    val saleOrderLine2 = SaleOrderLine("2",saleOrderHeader.soHeaderId, Date.valueOf(LocalDate.now()),part.PartId)
    val workOrder1 = WorkOrder("1",sequenceHeader.seqHeaderId,saleOrderLine1.soHeaderId,saleOrderLine1.soLineId,2)
    val workOrder2 = WorkOrder("2",sequenceHeader.seqHeaderId,saleOrderLine2.soHeaderId,saleOrderLine2.soLineId,4)
    val workOrder3 = WorkOrder("3",sequenceHeader.seqHeaderId,saleOrderLine1.soHeaderId,saleOrderLine1.soLineId,6)
    val staff1 = Staff("1","Steve")
    val staff2 = Staff("2","Adem")
    val workedOn1 = WorkedOn(staff1.staffId,sequenceLine1.seqHeaderId,sequenceLine1.seqLineId,workOrder1.workOrderId)
    val workedOn2 = WorkedOn(staff1.staffId,sequenceLine2.seqHeaderId,sequenceLine2.seqLineId,workOrder2.workOrderId)
    val workedOn3 = WorkedOn(staff2.staffId,sequenceLine1.seqHeaderId,sequenceLine1.seqLineId,workOrder1.workOrderId)

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

        saleOrderHeaderDAO = SaleOrderHeaderImpDAO(dbConnection)
        customerDAO = CustomerImpDAO(dbConnection)
        saleOrderLineDAO = SaleOrderLineImpDAO(dbConnection)
        partDAO = PartImpDAO(dbConnection)
        sequenceHeaderDAO = SequenceHeaderImpDAO(dbConnection)
        workOrderDAO = WorkOrderImpDAO(dbConnection)
        sequenceLineDAO = SequenceLineImpDAO(dbConnection)
        staffDAO = StaffImpDAO(dbConnection)
        workedOnDAO = WorkedOnImpDAO(dbConnection)
        workStationDAO = WorkStationImpDAO(dbConnection)

        partDAO.createPart(part)
        sequenceHeaderDAO.createSequenceHeader(sequenceHeader)
        workStationDAO.createWorkStation(workStation1)
        workStationDAO.createWorkStation(workStation2)
        sequenceLineDAO.createSequenceLines(sequenceLine1)
        sequenceLineDAO.createSequenceLines(sequenceLine2)
        customerDAO.createCustomer(customer)
        saleOrderHeaderDAO.createSaleOrderHeader(saleOrderHeader)
        saleOrderLineDAO.createSaleOrderLine(saleOrderLine1)
        saleOrderLineDAO.createSaleOrderLine(saleOrderLine2)
        workOrderDAO.createWorkOrder(workOrder1)
        workOrderDAO.createWorkOrder(workOrder2)
        workOrderDAO.createWorkOrder(workOrder3)
        staffDAO.createStaff(staff1)
        staffDAO.createStaff(staff2)
        workedOnDAO.createWorkedOn(workedOn1)
        workedOnDAO.createWorkedOn(workedOn2)
        workedOnDAO.createWorkedOn(workedOn3)
    }

    @Test
    fun getWorkedOns() {
        assertEquals(workedOnDAO.getWorkedOns(), listOf(workedOn1,workedOn2,workedOn3))
    }

    @Test
    fun getWorkedOn() {
        assertEquals(workedOnDAO.getWorkedOn(workedOn1.staffId,workedOn1.seqHeaderId,workedOn1.seqLineId,workedOn1.seqLineId),workedOn1)
    }

    @Test
    fun getWorkedOnByStaff() {
        assertEquals(workedOnDAO.getWorkedOnByStaff(staff1.staffId), listOf(workedOn1,workedOn2))
    }

    @Test
    fun getWorkedOnByWorkOrder() {
        assertEquals(workedOnDAO.getWorkedOnByWorkOrder(workOrder1.workOrderId), listOf(workedOn1,workedOn3))
    }
}