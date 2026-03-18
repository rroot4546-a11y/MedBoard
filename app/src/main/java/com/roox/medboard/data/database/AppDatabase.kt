package com.roox.medboard.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.roox.medboard.data.dao.MedDao
import com.roox.medboard.data.model.Bookmark
import com.roox.medboard.data.model.CachedTopic
import com.roox.medboard.data.model.ReadingProgress

@Database(
    entities = [CachedTopic::class, Bookmark::class, ReadingProgress::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medDao(): MedDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "medboard_db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}
