package com.nihil.emotiontag.database.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nihil.emotiontag.data.Emotions
import com.nihil.emotiontag.database.entities.EntryData
import com.nihil.emotiontag.database.repository.EntryRepository
import kotlinx.coroutines.launch
import java.util.UUID

class EntryViewModel(private val repository: EntryRepository) : ViewModel() {
    /**The list of all entries of the journal as [LiveData] [List] of [EntryData]**/
    val entries = repository.entries.asLiveData()

    /**
     * Function to search an specific entry by id
     *
     * @param id the id of the entry
     * @return the [EntryData] that coincides with the id as [LiveData]
     **/
    fun getEntryById(id: UUID): LiveData<EntryData> {
        return repository.getEntryById(id).asLiveData()
    }

    /**
     * Function to query last week emotions
     *
     * @return the list of last week entries' emotions as a [LiveData] [List] of [Int]
     **/
    fun getLastWeekEmotions(): LiveData<List<Int>> {
        return repository.getLastWeekEmotions().asLiveData()
    }

    /**
     * Function to insert a new [EntryData] in the database
     *
     * @param entryData the entry to insert
     **/
    fun insertEntry(entryData: EntryData) = viewModelScope.launch {
        repository.insertEntry(entryData)
    }

    /**
     * Function to delete an [EntryData] in the database
     *
     * @param entryData the entry to delete
     **/
    fun deleteEntry(entryData: EntryData) = viewModelScope.launch {
        repository.deleteEntry(entryData)
    }
}

/**
 * This class implements the ViewModelProvider.Factory interface and is used to instantiate
 * EntryViewModel objects.
 **/
class EntryViewModelFactory(private val repository: EntryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EntryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
