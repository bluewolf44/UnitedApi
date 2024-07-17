package com.unitedApi.dao

import com.unitedApi.model.Customer

interface CustomerDAO {



    fun getCustomers() : List<Customer>
    fun createCustomer(customer:Customer)
    fun getCustomer(id:String) : Customer
}