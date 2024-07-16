package com.unitedApi.model

import kotlinx.serialization.Serializable

@Serializable
data class Customer(val customerId:String,val name:String,val address:String)
