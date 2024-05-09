package com.nihil.emotiontag.util

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.Emotions
import java.util.Locale

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