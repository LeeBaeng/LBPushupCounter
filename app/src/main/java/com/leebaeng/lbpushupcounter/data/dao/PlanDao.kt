package com.leebaeng.lbpushupcounter.data.dao

import androidx.room.*
import com.leebaeng.lbpushupcounter.data.model.entity.Plan
import com.leebaeng.lbpushupcounter.data.model.entity.User

@Dao
interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlan(plan: Plan)

    @Update
    fun updatePlan(plan: Plan)

    @Query("select * from User")
    fun getUser() : User?
}