package com.leebaeng.lbpushupcounter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leebaeng.lbpushupcounter.data.dao.HistoryDao
import com.leebaeng.lbpushupcounter.data.dao.PlanDao
import com.leebaeng.lbpushupcounter.data.dao.UserDao
import com.leebaeng.lbpushupcounter.data.model.entity.History
import com.leebaeng.lbpushupcounter.data.model.entity.Plan
import com.leebaeng.lbpushupcounter.data.model.entity.User

@Database(entities = [User::class, History::class, Plan::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class DataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun userDao(): UserDao
    abstract fun planDao(): PlanDao
}

fun getDatabase(context: Context) : DataBase{
//    val migration1to2 = object : Migration(1,2){
//        override fun migrate(database: SupportSQLiteDatabase) {
//        }
//    }

    return Room.databaseBuilder(context, DataBase::class.java, "LBPushUpCoachDB")
//        .addMigrations(migration1to2)
        .build()
}