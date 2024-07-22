package com.unitedApi.dao

import com.unitedApi.model.SequenceLine

interface SequenceLineDAO {
    fun getSequencelines(): List<SequenceLine>
    fun createSequenceLines(sequenceLine: SequenceLine)
    fun getSequenceline(lineId:String,headerId:String): SequenceLine
    fun getSequenceLineByHeader(id:String): List<SequenceLine>
    fun getSequenceLineByWork(id:String): List<SequenceLine>
}