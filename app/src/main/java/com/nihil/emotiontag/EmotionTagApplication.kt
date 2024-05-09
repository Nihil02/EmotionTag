package com.nihil.emotiontag

import android.app.Application
import com.nihil.emotiontag.database.AppDatabase
import com.nihil.emotiontag.database.repository.EntryRepository

class EmotionTagApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { EntryRepository(database.entryDao()) }
}