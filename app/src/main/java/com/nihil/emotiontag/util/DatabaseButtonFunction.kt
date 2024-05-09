package com.nihil.emotiontag.util

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.ScreenData
import com.nihil.emotiontag.database.entities.EntryData
import com.nihil.emotiontag.database.vm.EntryViewModel

fun saveToDatabase(
    entryViewModel: EntryViewModel,
    entry: EntryData,
    context: Context,
    navController: NavController
) {
    entryViewModel.insertEntry(entry)
    Toast.makeText(
        context,
        ContextCompat.getString(context, R.string.msgSavedToDatabase),
        Toast.LENGTH_SHORT
    ).show()
    navController.navigate(ScreenData.EntriesScreen.title) {
        popUpTo(ScreenData.EntriesScreen.title) {
            inclusive = true
        }
    }
}