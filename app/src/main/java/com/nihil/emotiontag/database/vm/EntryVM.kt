package com.nihil.emotiontag.database.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nihil.emotiontag.database.entities.EntryData
import com.nihil.emotiontag.database.repository.EntryRepository
import kotlinx.coroutines.launch
import java.util.UUID

class EntryViewModel(private val repository: EntryRepository) : ViewModel() {
    val entries = repository.entries.asLiveData()

    suspend fun getEntryById(id: UUID): EntryData? {
        return repository.getEntryById(id)
    }

    fun insertEntry(entryData: EntryData) = viewModelScope.launch {
        repository.insertEntry(entryData)
    }
    fun updateEntry(entryData: EntryData) = viewModelScope.launch {
        repository.updateEntry(entryData)
    }
}

class EntryViewModelFactory(private val repository: EntryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EntryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

