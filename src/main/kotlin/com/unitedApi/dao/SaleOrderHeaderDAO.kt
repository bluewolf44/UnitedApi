package com.unitedApi.dao

import com.unitedApi.model.SaleOrderHeader

interface SaleOrderHeaderDAO {
    fun getSaleOrderHeaders() : List<SaleOrderHeader>
    fun createSaleOrderHeader(saleOrderHeader: SaleOrderHeader)
    fun getSaleOrderHeader(id:String):SaleOrderHeader
    fun getSaleOrderHeaderByCustomerId(id:String):List<SaleOrderHeader>
}