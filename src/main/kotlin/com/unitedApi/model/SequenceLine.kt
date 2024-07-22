package com.unitedApi.model

import kotlinx.serialization.Serializable

@Serializable
data class SequenceLine (val seqLineId:String,val seqHeaderId:String,val workStation:String)
