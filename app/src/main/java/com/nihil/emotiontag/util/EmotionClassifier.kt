package com.nihil.emotiontag.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nihil.emotiontag.data.Emotions
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.Locale

/**Object to represent a word and its associated values**/
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

    /**
     * Function to load the dictionary from the assets, the dictionary name must be the same as the
     * name of the json file
     *
     * @return the dictionary converted in an list of [EmotionScores] objects
     **/
    private fun loadDictionary(): List<EmotionScores>? {
        var filename = "data-eng.json"
        return try {
            if (Locale.getDefault().getLanguage() == "es") {
                filename = "data-spa.json"
            }

            context.assets.open(filename).use { inputStream ->
                val jsonString = inputStream.readBytes().toString(StandardCharsets.UTF_8)
                gson.fromJson(jsonString, emotionListType)
            }

        } catch (e: IOException) {
            println("Error: ${e.message}")
            null
        }
    }

    /**
     * Function to analyze the emotions expressed in the text, based on a dictionary with scores
     * associated with each word.
     *
     * @param text the text to be analyzed
     *
     * @return an [Emotions] enum value
     **/
    fun analyze(text: String): Emotions {
        // Loading data
        val dictionary = loadDictionary() ?: return Emotions.ERROR
        val words = text.lowercase().split("\\s+".toRegex())
        val totalScores = mutableMapOf(
            JOY to 0.0, SADNESS to 0.0, ANGER to 0.0, DISGUST to 0.0, FEAR to 0.0, SURPRISE to 0.0
        )

        // Processing each word
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

        // Returning a result
        val max = totalScores.maxByOrNull { it.value }?.key
        val min = totalScores.minByOrNull { it.value }?.key

        return if (max != min) {
            // Returns the emotion with the highest score
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
            // If the max and min emotion are the same, then no emotion is dominant
            /* TODO Modify this code so it can better handle ties between similar emotion */
            Emotions.NON_DOMINANT
        }
    }
}
