package com.nihil.emotiontag.database.repository

import androidx.annotation.WorkerThread
import com.nihil.emotiontag.database.dao.EntryDao
import com.nihil.emotiontag.database.entities.EntryData
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class EntryRepository(private val entryDao: EntryDao) {
    val entries: Flow<List<EntryData>> = entryDao.getAllEntries()

    fun getEntryById(id: UUID): Flow<EntryData> {
        return entryDao.getEntryById(id)
    }

    @WorkerThread
    suspend fun insertEntry(entryData: EntryData) {
        entryDao.insertEntry(entryData)
    }

    @WorkerThread
    suspend fun deleteEntry(entryData: EntryData) {
        entryDao.deleteEntry(entryData)
    }
}