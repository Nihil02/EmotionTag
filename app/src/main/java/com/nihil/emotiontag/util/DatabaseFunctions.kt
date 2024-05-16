package com.nihil.emotiontag.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.ScreenData
import com.nihil.emotiontag.database.entities.EntryData
import com.nihil.emotiontag.database.vm.EntryViewModel

/**
 * Function to save or update an entry into the Room Database
 *
 * @param entryViewModel
 * @param entry the information that is going to be saved in the database. Note: If the entry to be
 * saved has the same id as an existing entry, it will update the information of this entry.
 * @param context a [Context] object used to send a message with a Toast
 * @param navController a [NavController] object used to return to the previous page after saving
 * the data to the database
 * **/
fun saveToDatabase(
    entryViewModel: EntryViewModel,
    entry: EntryData,
    context: Context,
    navController: NavController
) {
    try {
        entryViewModel.insertEntry(entry)
        Toast.makeText(
            context,
            ContextCompat.getString(context, R.string.msgSavedToDatabase),
            Toast.LENGTH_SHORT
        ).show()
    } catch (error: Error) {
        Toast.makeText(
            context,
            "Error",
            Toast.LENGTH_SHORT
        ).show()
        Log.e("Error", "saveToDatabase() returned: $error")
    } finally {
        navController.navigate(ScreenData.EntriesScreenData.route) {
            popUpTo(ScreenData.EntriesScreenData.route) {
                inclusive = true
            }
        }
    }
}
/**
 * Function to delete an entry from the Room Database
 *
 * @param entryViewModel
 * @param entry the information that is going to be deleted from the database.
 * @param context a [Context] object used to send a message with a Toast
 * **/
fun deleteFromDatabase(
    entryViewModel: EntryViewModel,
    entry: EntryData,
    context: Context
) {
    try {
        entryViewModel.deleteEntry(entry)
        Toast.makeText(
            context,
            ContextCompat.getString(context, R.string.msgDeletedFromDatabase),
            Toast.LENGTH_SHORT
        ).show()
    } catch (error: Error) {
        Toast.makeText(
            context,
            "Error",
            Toast.LENGTH_SHORT
        ).show()
        Log.e("Error", "deleteFromDatabase() returned: $error")
    }
}