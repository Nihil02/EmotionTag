package com.nihil.emotiontag.ui.screens

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.EntryData
import com.nihil.emotiontag.util.RecognitionListenerAdapter
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddEntryScreen(navController: NavController = NavController(LocalContext.current)) {
    val speechPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val context = LocalContext.current
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }

    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var emotion by remember { mutableStateOf("No se ha analizado el sentimiento") }
    var ready by remember { mutableStateOf(false) }

    LaunchedEffect(speechRecognizer) {
        speechRecognizer.setRecognitionListener(object : RecognitionListenerAdapter() {
            override fun onResults(results: Bundle?) {
                val res = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!res.isNullOrEmpty()) {
                    text = res[0]
                }
            }
        })
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.lblEntryTitle)) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(30.dp))

            TextField(
                placeholder = { Text("") },
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(R.string.lblEntryText)) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))


            Row {
                Button(onClick = {
                    if (!speechPermissionState.status.isGranted) {
                        speechPermissionState.launchPermissionRequest()
                    } else {
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                        intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                        )
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start speaking...")
                        speechRecognizer.startListening(intent)
                    }
                }) {
                    Text(stringResource(R.string.btnTextRecord))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = { text = ""; ready = false }) {
                    Text(stringResource(R.string.btnTextCancel))
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(text = emotion)

            Spacer(modifier = Modifier.height(50.dp))

            Row {
                Button(
                    onClick = { EntryData(title, text, emotion) },
                    enabled = ready
                ) {
                    Text(stringResource(R.string.btnTextSave))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text(stringResource(R.string.btnTextAnalyze))
                }
            }
        }
    }
}


