package com.unitedApi.model

import kotlinx.serialization.Serializable
import java.sql.Date
import java.time.LocalDate

@Serializable
data class WorkedOn (val staffId:String, val seqHeaderId:String,val seqLineId:String, val workOrderId:String)
