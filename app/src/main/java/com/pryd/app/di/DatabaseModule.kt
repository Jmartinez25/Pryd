package com.pryd.app.di

import android.content.Context
import androidx.room.Room
import com.pryd.app.data.local.PrydDatabase
import com.pryd.app.data.local.dao.ActivityDao
import com.pryd.app.data.local.dao.PomodoroSessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PrydDatabase {
        return Room.databaseBuilder(
            context,
            PrydDatabase::class.java,
            "pryd_database"
        ).build()
    }

    @Provides
    fun provideActivityDao(database: PrydDatabase): ActivityDao {
        return database.activityDao
    }

    @Provides
    fun providePomodoroSessionDao(database: PrydDatabase): PomodoroSessionDao {
        return database.pomodoroSessionDao
    }
}