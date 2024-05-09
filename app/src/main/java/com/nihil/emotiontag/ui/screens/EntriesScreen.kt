package com.nihil.emotiontag.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.addEntryScreen
import com.nihil.emotiontag.database.vm.EntryViewModel
import com.nihil.emotiontag.ui.components.EntriesList
import com.nihil.emotiontag.ui.components.TopBar

@Composable
fun EntriesScreen(navController: NavController, entryViewModel: EntryViewModel) {
    val entries by entryViewModel.entries.observeAsState(emptyList())

    Scaffold(
        topBar = { TopBar(title = stringResource(R.string.scrTitleEntries)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(addEntryScreen.title) },
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

