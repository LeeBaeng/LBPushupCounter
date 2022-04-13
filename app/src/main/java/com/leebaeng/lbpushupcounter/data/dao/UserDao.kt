package com.leebaeng.lbpushupcounter.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leebaeng.lbpushupcounter.data.model.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUser(user: User)

    @Query("select * from User")
    fun getUser() : User?
}