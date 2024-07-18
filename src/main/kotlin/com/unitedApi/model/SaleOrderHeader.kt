package com.unitedApi.model

import kotlinx.serialization.Serializable

@Serializable
data class SaleOrderHeader(val soHeaderId:String, val customerId:String)