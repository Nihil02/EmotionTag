package com.nihil.emotiontag.util

import android.content.Context
import java.nio.charset.StandardCharsets
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

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

    private fun loadDictionary(): List<EmotionScores>? {
        return try {
            context.assets.open("data-spa.json").use { inputStream ->
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                gson.fromJson(jsonString, emotionListType)
            }
        } catch (e: IOException) {
            println("Error reading the file: ${e.message}")
            null
        }
    }

    fun analyze(text: String): String {
        val dictionary = loadDictionary() ?: return "Error loading dictionary"
        val words = text.lowercase().split("\\s+".toRegex())
        val totalScores = mutableMapOf(
            "joy" to 0.0,
            "sadness" to 0.0,
            "anger" to 0.0,
            "disgust" to 0.0,
            "fear" to 0.0,
            "surprise" to 0.0
        )

        words.forEach { word ->
            dictionary.find { it.word == word }?.let { scores ->
                totalScores["joy"] = totalScores["joy"]!! + scores.joy
                totalScores["sadness"] = totalScores["sadness"]!! + scores.sadness
                totalScores["anger"] = totalScores["anger"]!! + scores.anger
                totalScores["disgust"] = totalScores["disgust"]!! + scores.disgust
                totalScores["fear"] = totalScores["fear"]!! + scores.fear
                totalScores["surprise"] = totalScores["surprise"]!! + scores.surprise
            }
        }

        return totalScores.maxByOrNull { it.value }?.key ?: "No dominant emotion"
    }
}
