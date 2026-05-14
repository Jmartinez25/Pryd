package com.pryd.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pryd.app.data.local.dao.ActivityDao
import com.pryd.app.data.local.entity.ActivityEntity

@Database(
    entities = [
        ActivityEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PrydDatabase : RoomDatabase() {

    abstract val activityDao: ActivityDao

}