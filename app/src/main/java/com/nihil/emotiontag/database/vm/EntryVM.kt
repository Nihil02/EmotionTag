package com.nihil.emotiontag.database.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nihil.emotiontag.database.entities.EntryData
import com.nihil.emotiontag.database.repository.EntryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class EntryViewModel(private val repository: EntryRepository) : ViewModel() {
    val entries = repository.entries.asLiveData()

    fun getEntryById(id: UUID): LiveData<EntryData> {
        return repository.getEntryById(id).asLiveData()
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

