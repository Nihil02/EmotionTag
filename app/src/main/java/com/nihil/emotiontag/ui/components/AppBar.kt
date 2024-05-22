package com.nihil.emotiontag.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.ScreenData
import com.nihil.emotiontag.util.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onNavigationIconClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
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

@Composable
fun BottomBar() {
    val navController = LocalNavController.current

    BottomAppBar(
        actions = {
            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = { navController.navigate(ScreenData.EntriesScreenData.route) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_entries),
                        contentDescription = "Entries"
                    )
                    Text(
                        text = stringResource(R.string.scrTitleEntries),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = { navController.navigate(ScreenData.StatisticsScreenData.route) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_statistics),
                        contentDescription = "Statistics"
                    )
                    Text(
                        text = stringResource(R.string.scrTiitleStatistics),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    )
}