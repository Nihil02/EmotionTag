package com.nihil.emotiontag.ui.screens

import android.Manifest
import android.app.Activity
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.Emotions
import com.nihil.emotiontag.data.ScreenData
import com.nihil.emotiontag.database.entities.EntryData
import com.nihil.emotiontag.ui.components.EntryButtonsRow
import com.nihil.emotiontag.ui.components.TopBar
import com.nihil.emotiontag.util.EmotionClassifier
import com.nihil.emotiontag.util.LocalEntryViewModel
import com.nihil.emotiontag.util.LocalNavController
import com.nihil.emotiontag.util.getEmotionText
import com.nihil.emotiontag.util.saveToDatabase
import com.nihil.emotiontag.util.speechRecognition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.UUID

/**
 * App Screen
 *
 * Screen for editing an entry of the journal
 *
 * @param id the id of the entry to modify in string format
 **/
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EditEntryScreen(id: String) {
    val navController = LocalNavController.current
    val entryViewModel = LocalEntryViewModel.current
    val entry by entryViewModel.getEntryById(UUID.fromString(id)).observeAsState()

    val speechPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val context = LocalContext.current

    val (title, setTitle) = remember(entry) { mutableStateOf(entry?.title ?: "") }
    val (text, setText) = remember(entry) { mutableStateOf(entry?.text ?: "") }
    val (emotion, setEmotion) = remember(entry) {
        mutableIntStateOf(
            entry?.emotion ?: Emotions.NONE.value
        )
    }
    val date = entry?.date ?: LocalDate.now().toString()
    var isRecording by remember { mutableStateOf(false) }
    var isProcessing by remember { mutableStateOf(false) }

    val isReady by remember {
        derivedStateOf {
            title.isNotBlank() && text.isNotBlank() && emotion != Emotions.NONE.value && emotion != Emotions.ERROR.value
        }
    }

    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val res =
                    result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                        ?: ""
                setText(res)
            }
            isRecording = false
        }
    )

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.scrTitleEditEntry),
                onNavigationIconClick = {
                    if (!isProcessing) {
                        navController.navigate(ScreenData.EntriesScreenData.route) {
                            popUpTo(ScreenData.EntriesScreenData.route) {
                                inclusive = true
                            }
                        }
                    }
                })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = setTitle,
                    label = { Text(stringResource(R.string.lblEntryTitle)) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    singleLine = true
                )

                TextField(
                    placeholder = { Text("") },
                    value = text,
                    onValueChange = setText,
                    label = { Text(stringResource(R.string.lblEntryText)) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(150.dp)
                )

                Button(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    onClick = {
                        if (!isRecording) {
                            if (speechPermissionState.status.isGranted) {
                                speechRecognition(activityResultLauncher)
                                isRecording = true
                            } else {
                                speechPermissionState.launchPermissionRequest()
                            }
                        }
                    }
                ) {
                    Text(stringResource(R.string.btnTextRecord))
                }

                if (isProcessing) {
                    Text(stringResource(R.string.lblEntryEmotionProcessing))
                } else {
                    Text(getEmotionText(emotion))
                }

                EntryButtonsRow(
                    onActionButtonTitle = stringResource(R.string.btnTextSave),
                    isReady = isReady,
                    isProcessing = isProcessing,
                    onActionClick = {
                        val editedEntry = EntryData(
                            id = UUID.fromString(id),
                            title = title,
                            text = text,
                            emotion = emotion,
                            date = date
                        )
                        saveToDatabase(entryViewModel, editedEntry, context, navController)
                    },
                    onAnalyzeClick = {
                        isProcessing = true
                        CoroutineScope(Dispatchers.IO).launch {
                            val analyzedEmotion = EmotionClassifier(context).analyze(text)
                            withContext(Dispatchers.Main) {
                                setEmotion(analyzedEmotion.value)
                                isProcessing = false
                            }
                        }
                    })
            }
        }
    }
}
