package com.unitedApi.dao

import com.unitedApi.model.SequenceHeader

interface SequenceHeaderDAO
{
    fun getSequenceHeaders():List<SequenceHeader>
    fun createSequenceHeader(sequenceHeader:SequenceHeader)
    fun getSequenceHeader(id:String):SequenceHeader
}