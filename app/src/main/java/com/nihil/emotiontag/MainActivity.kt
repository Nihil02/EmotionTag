package com.nihil.emotiontag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
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
import com.nihil.emotiontag.util.LocalEntryViewModel
import com.nihil.emotiontag.util.LocalNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val entryViewModel: EntryViewModel by viewModels {
            EntryViewModelFactory((application as EmotionTagApplication).repository)
        }

        setContent {
            EmotionTagTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(
                    LocalEntryViewModel provides entryViewModel,
                    LocalNavController provides navController
                ) {
                    NavHost(navController, startDestination = ScreenData.EntriesScreenData.route) {
                        composable(ScreenData.EntriesScreenData.route) {
                            EntriesScreen()
                        }
                        composable(ScreenData.AddEntryScreenData.route) {
                            AddEntryScreen()
                        }
                        composable(
                            ScreenData.ShowEntryScreenData.route + "/{id}",
                            arguments = listOf(navArgument(name = "id") {
                                type = NavType.StringType
                            })
                        ) {
                            it.arguments?.getString("id")?.let { id ->
                                ShowEntryScreen(id)
                            }
                        }
                        composable(
                            ScreenData.UpdateEntryScreenData.route + "/{id}",
                            arguments = listOf(navArgument(name = "id") {
                                type = NavType.StringType
                            })
                        ) {
                            it.arguments?.getString("id")?.let { id ->
                                EditEntryScreen(id)
                            }
                        }
                    }
                }
            }
        }
    }
}
