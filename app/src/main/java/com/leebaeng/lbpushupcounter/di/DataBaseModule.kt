package com.leebaeng.lbpushupcounter.di

import android.app.Application
import androidx.room.Room
import com.leebaeng.lbpushupcounter.data.dao.HistoryDao
import com.leebaeng.lbpushupcounter.data.dao.PlanDao
import com.leebaeng.lbpushupcounter.data.dao.UserDao
import com.leebaeng.lbpushupcounter.data.db.DBAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
    ): DBAdapter.DataBase {
        return Room.databaseBuilder(application, DBAdapter.DataBase::class.java, "LBPushUpCoachDB")
//        .addMigrations(migration1to2)
            .build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(database: DBAdapter.DataBase): HistoryDao = database.historyDao()

    @Provides
    @Singleton
    fun provideUserDao(database: DBAdapter.DataBase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun providePlanDao(database: DBAdapter.DataBase): PlanDao =  database.planDao()
}