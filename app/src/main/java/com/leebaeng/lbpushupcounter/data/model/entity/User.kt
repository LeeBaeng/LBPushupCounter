package com.leebaeng.lbpushupcounter.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: String,
    @ColumnInfo var nickName: String = uid,
    @ColumnInfo var defaultTarget: Int = 0,
)