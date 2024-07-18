package com.unitedApi.model

import kotlinx.serialization.Serializable

@Serializable
data class SequenceHeader(val seqHeaderId:String,val partId:String)