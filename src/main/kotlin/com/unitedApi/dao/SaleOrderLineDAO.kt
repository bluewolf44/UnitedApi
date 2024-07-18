package com.unitedApi.dao

import com.unitedApi.model.SaleOrderLine

interface SaleOrderLineDAO {
    fun getSaleOrderLines():List<SaleOrderLine>
    fun createSaleOrderLine(saleOrderLine:SaleOrderLine)
    fun getSaleOrderLine(headerId:String,lineId:String):SaleOrderLine
    fun getSaleOrderLinesByHeader(id:String):List<SaleOrderLine>
}