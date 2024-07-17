package com.unitedApi

import com.unitedApi.dao.CustomerImpDAO
import com.unitedApi.dao.SaleOrderHeaderDAO
import com.unitedApi.dao.SaleOrderHeaderImpDAO
import com.unitedApi.model.Customer
import com.unitedApi.model.SaleOrderHeader
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.sql.Connection
import java.sql.DriverManager
import kotlin.test.assertContains

class SaleOrderHeaderDAOTest {

    lateinit var saleOrderHeaderDAO:SaleOrderHeaderDAO
    lateinit var customerDAO:CustomerImpDAO
    lateinit var dbConnection: Connection

    val customer1 = Customer("1","Dave","69th street")
    val customer2 = Customer("2","Sam","10th street")
    val saleOrderHeader1 = SaleOrderHeader("1","1")
    val saleOrderHeader2 = SaleOrderHeader("2","2")
    val saleOrderHeader3 = SaleOrderHeader("3","1")

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
        saleOrderHeaderDAO = SaleOrderHeaderImpDAO(dbConnection).apply { runBlocking {  } }
        customerDAO = CustomerImpDAO(dbConnection).apply { runBlocking {  } }

        customerDAO.createCustomer(customer1)
        customerDAO.createCustomer(customer2)
        saleOrderHeaderDAO.createSaleOrderHeader(saleOrderHeader1)
        saleOrderHeaderDAO.createSaleOrderHeader(saleOrderHeader2)
        saleOrderHeaderDAO.createSaleOrderHeader(saleOrderHeader3)
    }

    @Test
    fun getSaleOrderHeaders() {
        assertEquals(saleOrderHeaderDAO.getSaleOrderHeaders(),listOf(saleOrderHeader1,saleOrderHeader2,saleOrderHeader3))
    }

    @Test
    fun getSaleOrderHeader() {
        assertEquals(saleOrderHeaderDAO.getSaleOrderHeader(saleOrderHeader1.seqHeaderId),saleOrderHeader1)
    }

    @Test
    fun getSaleOrderHeaderByCustomer()
    {
        assertEquals(saleOrderHeaderDAO.getSaleOrderHeaderByCustomerId(customer1.customerId), listOf(saleOrderHeader1,saleOrderHeader3))
    }
}