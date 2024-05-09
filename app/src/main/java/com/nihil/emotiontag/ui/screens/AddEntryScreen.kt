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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.Emotions
import com.nihil.emotiontag.data.ScreenData
import com.nihil.emotiontag.database.entities.EntryData
import com.nihil.emotiontag.database.vm.EntryViewModel
import com.nihil.emotiontag.ui.components.EntryButtonsRow
import com.nihil.emotiontag.ui.components.TopBar
import com.nihil.emotiontag.util.EmotionClassifier
import com.nihil.emotiontag.util.getEmotionText
import com.nihil.emotiontag.util.saveToDatabase
import com.nihil.emotiontag.util.speechRecognition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddEntryScreen(navController: NavController, entryViewModel: EntryViewModel) {
    val speechPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var emotion by remember { mutableIntStateOf(Emotions.NONE.value) }
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
                text = res
            }
            isRecording = false
        }
    )

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.scrTitleAddEntry),
                onNavigationIconClick = {
                    if (!isProcessing) {
                        navController.navigate(ScreenData.EntriesScreen.title) {
                            popUpTo(ScreenData.EntriesScreen.title) {
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
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.lblEntryTitle)) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    singleLine = true
                )

                TextField(
                    placeholder = { Text("") },
                    value = text,
                    onValueChange = { text = it },
                    label = { Text(stringResource(R.string.lblEntryText)) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(150.dp)
                )

                Button(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    onClick = {
                        if (!isRecording) {
                            speechRecognition(activityResultLauncher, speechPermissionState)
                            isRecording = true
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
                        val entry = EntryData(title = title, text = text, emotion = emotion)
                        saveToDatabase(entryViewModel, entry, context, navController)
                    },
                    onAnalyzeClick = {
                        isProcessing = true
                        CoroutineScope(Dispatchers.IO).launch {
                            val analyzedEmotion = EmotionClassifier(context).analyze(text)
                            withContext(Dispatchers.Main) {
                                emotion = analyzedEmotion.value
                                isProcessing = false
                            }
                        }
                    })
            }
        }
    }
}
