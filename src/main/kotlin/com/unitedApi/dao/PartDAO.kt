package com.unitedApi.dao

import com.unitedApi.model.Part

interface PartDAO {
    fun getParts():List<Part>
    fun createPart(part:Part)
    fun getPart(id:String):Part
}