package com.leebaeng.lbpushupcounter.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.leebaeng.lbpushupcounter.data.model.SetInfo
import com.leebaeng.lbpushupcounter.data.model.SetResult

class TypeConverter {
    @TypeConverter
    fun fromDbSetResultList(value: String?): ArrayList<SetResult>? = Gson().fromJson(value, Array<SetResult>::class.java)?.toCollection(ArrayList())

    @TypeConverter
    fun toDbSetResultList(value: ArrayList<SetResult>?): String = Gson().toJson(value)

    @TypeConverter
    fun fromDbSetInfo(value: String?): ArrayList<SetInfo>? = Gson().fromJson(value, Array<SetInfo>::class.java)?.toCollection(ArrayList())

    @TypeConverter
    fun toDbSetInfo(value: ArrayList<SetInfo>?): String = Gson().toJson(value)
}