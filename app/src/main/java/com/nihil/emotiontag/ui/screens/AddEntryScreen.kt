package com.nihil.emotiontag.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.Emotions
import com.nihil.emotiontag.data.entriesScreen
import com.nihil.emotiontag.database.entities.EntryData
import com.nihil.emotiontag.database.vm.EntryViewModel
import com.nihil.emotiontag.ui.components.TopBar
import com.nihil.emotiontag.util.EmotionClassifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddEntryScreen(navController: NavController, entryViewModel: EntryViewModel) {
    val speechPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var emotion by remember { mutableStateOf(Emotions.NONE.value) }
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
                        navController.navigate(entriesScreen.title) {
                            popUpTo(entriesScreen.title) {
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

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            val entry = EntryData(title = title, text = text, emotion = emotion)
                            saveToDatabase(entryViewModel, entry, context, navController)
                        },
                        enabled = isReady && !isProcessing
                    ) {
                        Text(stringResource(R.string.btnTextSave))
                    }
                    Button(
                        onClick = {
                            isProcessing = true
                            CoroutineScope(Dispatchers.IO).launch {
                                val analyzedEmotion = EmotionClassifier(context).analyze(text)
                                withContext(Dispatchers.Main) {
                                    emotion = analyzedEmotion.value
                                    isProcessing = false
                                }
                            }
                        },
                        enabled = !isProcessing
                    ) {
                        Text(stringResource(R.string.btnTextAnalyze))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun speechRecognition(
    activityResultLauncher: ActivityResultLauncher<Intent>,
    speechPermissionState: PermissionState
) {
    if (!speechPermissionState.status.isGranted) {
        speechPermissionState.launchPermissionRequest()
    } else {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo")
        }
        activityResultLauncher.launch(intent)
    }
}

private fun saveToDatabase(
    entryViewModel: EntryViewModel,
    entry: EntryData,
    context: Context,
    navController: NavController
) {
    entryViewModel.insertEntry(entry)
    Toast.makeText(
        context,
        getString(context, R.string.msgSavedToDatabase),
        Toast.LENGTH_SHORT
    ).show()
    navController.navigate(entriesScreen.title) {
        popUpTo(entriesScreen.title) {
            inclusive = true
        }
    }
}

@Composable
fun getEmotionText(emotion: Int): String {
    return when (emotion) {
        Emotions.JOY.value -> stringResource(R.string.emoJoy)
        Emotions.SADNESS.value -> stringResource(R.string.emoSadness)
        Emotions.ANGER.value -> stringResource(R.string.emoAnger)
        Emotions.DISGUST.value -> stringResource(R.string.emoDisgust)
        Emotions.FEAR.value -> stringResource(R.string.emoFear)
        Emotions.SURPRISE.value -> stringResource(R.string.emoSurprise)
        Emotions.NON_DOMINANT.value -> stringResource(R.string.emoNonDominant)
        Emotions.NONE.value -> stringResource(R.string.emoNone)
        else -> Emotions.ERROR.name
    }
}