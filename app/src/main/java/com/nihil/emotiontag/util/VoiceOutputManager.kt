package com.nihil.emotiontag.util

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

/**
 * Class to manage the text2speech functions of the app
 **/
class VoiceOutputManager {
    private lateinit var textToSpeech: TextToSpeech

    fun init(context: Context) {
        textToSpeech = TextToSpeech(context) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.getDefault()
            }
        }
    }

    fun speak(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun destroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}