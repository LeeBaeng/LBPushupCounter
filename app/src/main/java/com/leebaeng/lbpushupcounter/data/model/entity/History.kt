package com.leebaeng.lbpushupcounter.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leebaeng.lbpushupcounter.data.model.SetResult

@Entity
data class History(
    @PrimaryKey val date: String, //YYYYMMDD
    @ColumnInfo val totalCount: Long?,
    @ColumnInfo val totalTime: Long?,
    @ColumnInfo val infoPerSet:ArrayList<SetResult>?,
)
