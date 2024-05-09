package com.nihil.emotiontag.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nihil.emotiontag.database.entities.EntryData
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface EntryDao {
    @Query("SELECT * FROM entrydata")
    fun getAllEntries(): Flow<List<EntryData>>

    @Query("SELECT * FROM entrydata WHERE id LIKE :id")
    fun getEntryById(id: UUID): EntryData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: EntryData)

    @Update
    suspend fun updateEntry(entry: EntryData)
}
