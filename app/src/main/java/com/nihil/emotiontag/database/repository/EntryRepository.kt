package com.nihil.emotiontag.database.repository

import androidx.annotation.WorkerThread
import com.nihil.emotiontag.database.dao.EntryDao
import com.nihil.emotiontag.database.entities.EntryData
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class EntryRepository(private val entryDao: EntryDao) {
    val entries: Flow<List<EntryData>> = entryDao.getAllEntries()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getEntryById(id: UUID): EntryData {
        return entryDao.getEntryById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertEntry(entryData: EntryData) {
        entryDao.insertEntry(entryData)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateEntry(entryData: EntryData) {
        entryDao.updateEntry(entryData)
    }
}