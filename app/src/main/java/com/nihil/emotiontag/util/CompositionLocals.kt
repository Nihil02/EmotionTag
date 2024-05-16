package com.nihil.emotiontag.util

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import com.nihil.emotiontag.database.vm.EntryViewModel

/* View Model */
val LocalEntryViewModel = compositionLocalOf<EntryViewModel> {
    error("No EntryViewModel provided")
}

/* NavController */
val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController provided")
}