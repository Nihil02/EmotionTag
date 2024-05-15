package com.nihil.emotiontag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nihil.emotiontag.data.ScreenData
import com.nihil.emotiontag.database.vm.EntryViewModel
import com.nihil.emotiontag.database.vm.EntryViewModelFactory
import com.nihil.emotiontag.ui.screens.AddEntryScreen
import com.nihil.emotiontag.ui.screens.EditEntryScreen
import com.nihil.emotiontag.ui.screens.EntriesScreen
import com.nihil.emotiontag.ui.screens.ShowEntryScreen
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
                    composable(
                        ScreenData.ShowEntryScreen.title + "/{id}",
                        arguments = listOf(navArgument(name = "id") {
                            type = NavType.StringType
                        })
                    ) {
                        it.arguments?.getString("id")?.let { id ->
                            ShowEntryScreen(
                                navController,
                                entryViewModel,
                                id
                            )
                        }
                    }
                    composable(
                        ScreenData.UpdateEntryScreen.title + "/{id}",
                        arguments = listOf(navArgument(name = "id") {
                            type = NavType.StringType
                        })
                    ) {
                        it.arguments?.getString("id")?.let { id ->
                            EditEntryScreen(
                                navController,
                                entryViewModel,
                                id
                            )
                        }
                    }
                }
            }
        }
    }
}
