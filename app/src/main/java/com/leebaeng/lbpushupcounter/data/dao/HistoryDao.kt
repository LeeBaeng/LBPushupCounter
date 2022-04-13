package com.leebaeng.lbpushupcounter.data.dao

import androidx.room.*
import com.leebaeng.lbpushupcounter.data.model.entity.History

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)

    @Update
    fun updateHistory(history: History)

    @Query("select * from History where date > :timeFrom")
    fun getHistory(timeFrom: Long) : History?
}