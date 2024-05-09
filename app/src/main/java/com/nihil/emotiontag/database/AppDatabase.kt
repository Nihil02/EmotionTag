package com.nihil.emotiontag.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nihil.emotiontag.database.dao.EntryDao
import com.nihil.emotiontag.database.entities.EntryData

@Database(entities = [EntryData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "EmotionTagDataBase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
