package com.unitedApi.model

import kotlinx.serialization.Serializable

@Serializable
data class Part(val partId:String,val inventory:Int,val name:String)