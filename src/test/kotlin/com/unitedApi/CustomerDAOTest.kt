package com.unitedApi

import com.unitedApi.dao.CustomerDAO
import com.unitedApi.dao.CustomerImpDAO
import com.unitedApi.model.Customer
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.sql.Connection
import java.sql.DriverManager
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class CustomerDAOTest {

    lateinit var customerDAO: CustomerDAO
    lateinit var dbConnection:Connection

    val customer = Customer("1","Dave","69 street")

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
            "jdbc:postgresql://"+postgreContainer.getHost()+":"+postgreContainer.getFirstMappedPort()+"/"+postgreContainer.getDatabaseName(),
            postgreContainer.getUsername(),postgreContainer.getPassword()
        )
        customerDAO = CustomerImpDAO(dbConnection)

        customerDAO.createCustomer(customer)

    }


    @Test // Test getCustomer and createCustomer
    fun getCustomersTest()
    {
        assertContains(customerDAO.getCustomers(),customer)
    }

    @Test
    fun getCustomerTest()
    {
        assertEquals(customerDAO.getCustomer(customer.customerId),customer)
    }
}