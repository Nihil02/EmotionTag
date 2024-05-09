package com.nihil.emotiontag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nihil.emotiontag.data.ScreenData
import com.nihil.emotiontag.database.vm.EntryViewModel
import com.nihil.emotiontag.database.vm.EntryViewModelFactory
import com.nihil.emotiontag.ui.screens.AddEntryScreen
import com.nihil.emotiontag.ui.screens.EntriesScreen
import com.nihil.emotiontag.ui.theme.EmotionTagTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val entryViewModel: EntryViewModel by viewModels {
            EntryViewModelFactory((application as EmotionTagApplication).repository)
        }

        setContent {
            val navController = rememberNavController()

            EmotionTagTheme {
                NavHost(navController, startDestination = ScreenData.EntriesScreen.title) {
                    composable(ScreenData.EntriesScreen.title) {
                        EntriesScreen(
                            navController,
                            entryViewModel
                        )
                    }
                    composable(ScreenData.AddEntryScreen.title) {
                        AddEntryScreen(
                            navController,
                            entryViewModel
                        )
                    }
                }
            }
        }
    }
}
