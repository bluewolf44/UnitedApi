package com.unitedApi.model

import kotlinx.serialization.Serializable

@Serializable
data class WorkOrder (var workOrderId:String,var seqHeaderId:String,var soHeaderID:String,var quanity:Int)