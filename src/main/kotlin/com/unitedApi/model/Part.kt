package com.unitedApi.model

import kotlinx.serialization.Serializable

@Serializable
data class Part(val PartId:String,val inventory:Int,val name:String)