package com.nihil.emotiontag.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.ScreenData
import com.nihil.emotiontag.ui.components.TopBar
import com.nihil.emotiontag.util.LocalEntryViewModel
import com.nihil.emotiontag.util.LocalNavController
import com.nihil.emotiontag.util.VoiceOutputManager
import com.nihil.emotiontag.util.getEmotionText
import java.util.UUID

/**
 * App Screen
 *
 * Screen for showing an entry of the journal
 *
 * @param id the id of the entry to be shown in string format
 **/
@Composable
fun ShowEntryScreen(id: String) {
    val navController = LocalNavController.current
    val entry by LocalEntryViewModel.current.getEntryById(UUID.fromString(id)).observeAsState()
    val context = LocalContext.current
    val voiceManager = VoiceOutputManager()
    voiceManager.init(LocalContext.current)

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.srcTitleShowEntry),
                onNavigationIconClick = {
                    navController.navigate(ScreenData.EntriesScreenData.route) {
                        popUpTo(ScreenData.EntriesScreenData.route) {
                            inclusive = true
                        }
                    }
                })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                if (entry != null) {
                    Text(
                        text = entry!!.title,
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = stringResource(id = R.string.lblEntryShowEmotion) + getEmotionText(
                            entry!!.emotion
                        ),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                    Button(onClick = { voiceManager.speak(entry!!.text) }) {
                        Text(text = stringResource(id = R.string.btnReadText))
                    }
                    Text(
                        text = entry!!.text,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                } else {
                    Text(stringResource(R.string.lblEntryLoading))
                }
            }
        }
    }
}
