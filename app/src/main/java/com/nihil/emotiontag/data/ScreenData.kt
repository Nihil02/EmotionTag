package com.nihil.emotiontag.data

import androidx.compose.runtime.Composable
import com.nihil.emotiontag.ui.screens.AddEntryScreen

interface ScreenData {
    val title: String
}

object entriesScreen: ScreenData {
    override val title: String = "entries"
}

object addEntryScreen: ScreenData {
    override val title: String = "addEntry"
}