package com.unitedApi.dao

import com.unitedApi.model.Customer

interface CustomerDAO {

    suspend fun getCustomers() : List<Customer>
}