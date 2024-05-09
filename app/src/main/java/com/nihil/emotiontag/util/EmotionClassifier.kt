package com.nihil.emotiontag.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nihil.emotiontag.data.Emotions
import java.io.IOException
import java.nio.charset.StandardCharsets

data class EmotionScores(
    val word: String,
    val anger: Double,
    val disgust: Double,
    val fear: Double,
    val joy: Double,
    val sadness: Double,
    val surprise: Double
)

class EmotionClassifier(private val context: Context) {
    private val gson = Gson()
    private val emotionListType = object : TypeToken<List<EmotionScores>>() {}.type

    companion object {
        const val JOY = "joy"
        const val SADNESS = "sadness"
        const val ANGER = "anger"
        const val DISGUST = "disgust"
        const val FEAR = "fear"
        const val SURPRISE = "surprise"
    }

    private fun loadDictionary(): List<EmotionScores>? {
        return try {
            context.assets.open("data-spa.json").use { inputStream ->
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                gson.fromJson(jsonString, emotionListType)
            }
        } catch (e: IOException) {
            println("Error: ${e.message}")
            null
        }
    }

    fun analyze(text: String): Emotions {
        val dictionary = loadDictionary() ?: return Emotions.ERROR
        val words = text.lowercase().split("\\s+".toRegex())
        val totalScores = mutableMapOf(
            JOY to 0.0, SADNESS to 0.0, ANGER to 0.0, DISGUST to 0.0, FEAR to 0.0, SURPRISE to 0.0
        )

        words.forEach { word ->
            dictionary.find { it.word == word }?.let { scores ->
                totalScores[JOY] = totalScores[JOY]!! + scores.joy
                totalScores[SADNESS] = totalScores[SADNESS]!! + scores.sadness
                totalScores[ANGER] = totalScores[ANGER]!! + scores.anger
                totalScores[DISGUST] = totalScores[DISGUST]!! + scores.disgust
                totalScores[FEAR] = totalScores[FEAR]!! + scores.fear
                totalScores[SURPRISE] = totalScores[SURPRISE]!! + scores.surprise
            }
        }

        val max = totalScores.maxByOrNull { it.value }?.key
        val min = totalScores.minByOrNull { it.value }?.key

        return if (max != min) {
            when (max) {
                JOY -> Emotions.JOY
                SADNESS -> Emotions.SADNESS
                ANGER -> Emotions.ANGER
                DISGUST -> Emotions.DISGUST
                FEAR -> Emotions.FEAR
                SURPRISE -> Emotions.SURPRISE

                else -> Emotions.ERROR
            }
        } else {
            Emotions.NON_DOMINANT
        }
    }
}
