package com.unitedApi.dao

import com.unitedApi.model.Sales

interface SalesDAO {

    suspend fun getSales(): List<String>
}