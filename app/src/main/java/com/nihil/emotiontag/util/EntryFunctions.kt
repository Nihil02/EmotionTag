package com.nihil.emotiontag.util

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.nihil.emotiontag.R
import com.nihil.emotiontag.data.Emotions
import java.util.Locale

/**
 * Function to launch the speech recognition activity
 **/
fun speechRecognition(
    activityResultLauncher: ActivityResultLauncher<Intent>
) {
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

@Composable
fun getEmotionColor(emotion: Int): Color {
    return when (emotion) {
        Emotions.JOY.value -> Emotions.JOY.color
        Emotions.SADNESS.value -> Emotions.SADNESS.color
        Emotions.ANGER.value -> Emotions.ANGER.color
        Emotions.DISGUST.value -> Emotions.DISGUST.color
        Emotions.FEAR.value -> Emotions.FEAR.color
        Emotions.SURPRISE.value -> Emotions.SURPRISE.color
        Emotions.NON_DOMINANT.value -> Emotions.NON_DOMINANT.color
        Emotions.NONE.value -> Emotions.NONE.color
        else -> Emotions.ERROR.color
    }
}