package com.roox.medboard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TopicEntity::class], version = 1, exportSchema = false)
abstract class MedBoardDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao

    companion object {
        @Volatile
        private var INSTANCE: MedBoardDatabase? = null

        fun getDatabase(context: Context): MedBoardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedBoardDatabase::class.java,
                    "medboard_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
