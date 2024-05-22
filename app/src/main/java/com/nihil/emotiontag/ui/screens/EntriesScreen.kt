package com.nihil.emotiontag.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.ScreenData
import com.nihil.emotiontag.ui.components.BottomBar
import com.nihil.emotiontag.ui.components.EntriesList
import com.nihil.emotiontag.ui.components.TopBar
import com.nihil.emotiontag.util.LocalEntryViewModel
import com.nihil.emotiontag.util.LocalNavController

/**
 * App Screen
 *
 * Screen for seeing all the journal's entry
 **/
@Composable
fun EntriesScreen() {
    val navController = LocalNavController.current
    val entries by LocalEntryViewModel.current.entries.observeAsState(emptyList())

    Scaffold(
        topBar = { TopBar(title = stringResource(R.string.scrTitleEntries)) },
        bottomBar = { BottomBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ScreenData.AddEntryScreenData.route) },
            ) {
                Icon(Icons.Filled.Add, "Add an entry")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            EntriesList(entries)
        }
    }
}

