package com.nihil.emotiontag.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nihil.emotiontag.database.entities.EntryData
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface EntryDao {
    @Query("SELECT * FROM EntryData")
    fun getAllEntries(): Flow<List<EntryData>>

    @Query("SELECT * FROM EntryData WHERE id = :id")
    fun getEntryById(id: UUID): Flow<EntryData>

    @Query("SELECT * FROM EntryData WHERE date BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime');")
    fun getLastWeekEntries(): Flow<List<EntryData>>

    @Query("SELECT emotion FROM EntryData WHERE date BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime');")
    fun getLastWeekEmotions(): Flow<List<Int>>

    @Query("SELECT emotion FROM EntryData WHERE date BETWEEN datetime('now', 'start of month') AND datetime('now', 'localtime');")
    fun getLastMonthEmotions(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: EntryData)

    @Delete
    suspend fun deleteEntry(entry: EntryData)
}
