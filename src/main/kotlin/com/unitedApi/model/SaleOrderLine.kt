package com.unitedApi.model

import java.util.Date

data class SaleOrderLine(val soLineId:String,val soHeaderId:String,val dateDue:Date,val partId:String)