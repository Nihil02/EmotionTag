package com.nihil.emotiontag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nihil.emotiontag.data.addEntryScreen
import com.nihil.emotiontag.data.entriesScreen
import com.nihil.emotiontag.database.vm.EntryViewModel
import com.nihil.emotiontag.database.vm.EntryViewModelFactory
import com.nihil.emotiontag.ui.screens.AddEntryScreen
import com.nihil.emotiontag.ui.screens.EntriesScreen
import com.nihil.emotiontag.ui.theme.EmotionTagTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val entryViewModel: EntryViewModel by viewModels{
            EntryViewModelFactory((application as EmotionTagApplication).repository)
        }

        setContent {
            val navController = rememberNavController()

            EmotionTagTheme {
                NavHost(navController, startDestination = entriesScreen.title) {
                    composable(entriesScreen.title) { EntriesScreen(navController, entryViewModel) }
                    composable(addEntryScreen.title) { AddEntryScreen(navController, entryViewModel) }
                }
            }
        }
    }
}
