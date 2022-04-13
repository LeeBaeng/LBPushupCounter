package com.leebaeng.lbpushupcounter.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leebaeng.lbpushupcounter.data.model.SetInfo

@Entity
data class Plan(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo var name: String?,
    @ColumnInfo val setList: ArrayList<SetInfo>?
)
