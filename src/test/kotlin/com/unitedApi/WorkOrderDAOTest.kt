package com.unitedApi

import com.unitedApi.dao.*
import com.unitedApi.model.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.sql.Connection
import java.sql.Date
import java.sql.DriverManager
import java.time.LocalDate

class WorkOrderDAOTest {

    lateinit var saleOrderHeaderDAO: SaleOrderHeaderDAO
    lateinit var customerDAO: CustomerImpDAO
    lateinit var saleOrderLineDAO: SaleOrderLineDAO
    lateinit var partDAO: PartDAO
    lateinit var sequenceHeaderDAO: SequenceHeaderDAO
    lateinit var workOrderDAO: WorkOrderDAO
    lateinit var dbConnection: Connection

    val part = Part("1", 50, "Cheese Balls")
    val sequenceHeader = SequenceHeader("1",part.partId)
    val customer = Customer("1","Dave","69th street")
    val saleOrderHeader = SaleOrderHeader("1",customer.customerId)
    val saleOrderLine1 = SaleOrderLine("1",saleOrderHeader.soHeaderId, Date.valueOf(LocalDate.now()),part.partId)
    val saleOrderLine2 = SaleOrderLine("2",saleOrderHeader.soHeaderId, Date.valueOf(LocalDate.now()),part.partId)
    val workOrder1 = WorkOrder("1",sequenceHeader.seqHeaderId,saleOrderLine1.soHeaderId,saleOrderLine1.soLineId,2)
    val workOrder2 = WorkOrder("2",sequenceHeader.seqHeaderId,saleOrderLine2.soHeaderId,saleOrderLine2.soLineId,4)
    val workOrder3 = WorkOrder("3",sequenceHeader.seqHeaderId,saleOrderLine1.soHeaderId,saleOrderLine1.soLineId,6)


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

        customerDAO.createCustomer(customer)
        saleOrderHeaderDAO.createSaleOrderHeader(saleOrderHeader)
        partDAO.createPart(part)
        sequenceHeaderDAO.createSequenceHeader(sequenceHeader)
        saleOrderLineDAO.createSaleOrderLine(saleOrderLine1)
        saleOrderLineDAO.createSaleOrderLine(saleOrderLine2)
        workOrderDAO.createWorkOrder(workOrder1)
        workOrderDAO.createWorkOrder(workOrder2)
        workOrderDAO.createWorkOrder(workOrder3)
    }

    @Test
    fun getWorkOrders() {
        assertEquals(workOrderDAO.getWorkOrders(), listOf(workOrder1,workOrder2,workOrder3))
    }

    @Test
    fun getWorkOrder() {
        assertEquals(workOrderDAO.getWorkOrder(workOrder1.workOrderId),workOrder1)
    }

    @Test
    fun getWorkOrderBySaleLine() {
        assertEquals(workOrderDAO.getWorkOrderBySaleLine(saleOrderLine1.soHeaderId,saleOrderLine1.soLineId), listOf(workOrder1,workOrder3))
    }
}