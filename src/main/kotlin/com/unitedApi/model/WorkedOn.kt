package com.unitedApi.model

import kotlinx.serialization.Serializable

@Serializable
data class WorkedOn (val staffID:String, val seqLineID:String, val workOrderId:String)