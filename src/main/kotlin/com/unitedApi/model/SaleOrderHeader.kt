package com.unitedApi.model

import kotlinx.serialization.Serializable

@Serializable
data class SaleOrderHeader(val seqHeaderId:String, val customerId:String)