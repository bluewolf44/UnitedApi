package com.unitedApi

import com.unitedApi.dao.*
import com.unitedApi.model.Customer
import com.unitedApi.model.Part
import com.unitedApi.model.SaleOrderHeader
import com.unitedApi.model.SaleOrderLine
import kotlinx.coroutines.runBlocking
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

class SaleOrderLineDAOTest {

    lateinit var saleOrderHeaderDAO: SaleOrderHeaderDAO
    lateinit var customerDAO: CustomerImpDAO
    lateinit var saleOrderLineDAO: SaleOrderLineDAO
    lateinit var partDAO: PartDAO
    lateinit var dbConnection: Connection

    val customer1 = Customer("1","Dave","69th street")
    val customer2 = Customer("2","Sam","10th street")
    val saleOrderHeader1 = SaleOrderHeader("1",customer1.customerId)
    val saleOrderHeader2 = SaleOrderHeader("2",customer2.customerId)
    val part1 = Part("1", 50, "Cheese Balls")
    val part2 = Part("2", 150, "Peanut butter")
    val saleOrderLine1 = SaleOrderLine("1",saleOrderHeader1.soHeaderId, Date.valueOf(LocalDate.now()),part1.PartId)
    val saleOrderLine2 = SaleOrderLine("1",saleOrderHeader2.soHeaderId, Date.valueOf(LocalDate.now()),part2.PartId)
    val saleOrderLine3 = SaleOrderLine("2",saleOrderHeader1.soHeaderId, Date.valueOf(LocalDate.now()),part2.PartId)

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

        customerDAO.createCustomer(customer1)
        customerDAO.createCustomer(customer2)
        saleOrderHeaderDAO.createSaleOrderHeader(saleOrderHeader1)
        saleOrderHeaderDAO.createSaleOrderHeader(saleOrderHeader2)
        partDAO.createPart(part1)
        partDAO.createPart(part2)
        saleOrderLineDAO.createSaleOrderLine(saleOrderLine1)
        saleOrderLineDAO.createSaleOrderLine(saleOrderLine2)
        saleOrderLineDAO.createSaleOrderLine(saleOrderLine3)
    }

    @Test
    fun getSaleOrderLines() {
        assertEquals(saleOrderLineDAO.getSaleOrderLines(),listOf(saleOrderLine1,saleOrderLine2,saleOrderLine3))
    }

    @Test
    fun getSaleOrderLine() {
        assertEquals(saleOrderLineDAO.getSaleOrderLine(saleOrderLine1.soHeaderId,saleOrderLine1.soLineId),saleOrderLine1)
    }

    @Test
    fun getSaleOrderLinesByHeader() {
        assertEquals(saleOrderLineDAO.getSaleOrderLinesByHeader(saleOrderHeader1.soHeaderId),listOf(saleOrderLine1,saleOrderLine3))
    }
}