package com.nihil.emotiontag.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.nihil.emotiontag.R
import com.nihil.emotiontag.database.entities.EntryData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onNavigationIconClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.Black,
        ),
        title = {
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (onNavigationIconClick != null) {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Return"
                    )
                }
            }
        },
    )
}

/* TODO Add a function for navigation and an animation for changing the view */
@Composable
fun BottomBar() {
    BottomAppBar(
        actions = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        Icons.Filled.Create,
                        contentDescription = "Something"
                    )
                }
                Text(
                    text = stringResource(R.string.scrTitleEntries),
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { /* TODO */ }
                ) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Something"
                    )
                }
            }
        }
    )
}


@Preview
@Composable
fun Preview_AppBar(
) {
    Scaffold(
        topBar = { TopBar(title = "Test") },
        bottomBar = { BottomBar() }
    )
    { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            EntriesList(
                listOf(
                    EntryData(title = "Hola", text = "Texto de prueba"),
                    EntryData(title = "Hola", text = "Texto de prueba")
                ),
                rememberNavController()
            )
        }
    }
}