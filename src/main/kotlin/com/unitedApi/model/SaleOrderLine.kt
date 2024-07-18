package com.unitedApi.model

import java.sql.Date

data class SaleOrderLine(val soLineId:String,val soHeaderId:String,val dateDue:Date,val partId:String)