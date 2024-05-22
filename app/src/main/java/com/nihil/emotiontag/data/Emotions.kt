package com.nihil.emotiontag.data

import androidx.compose.ui.graphics.Color

/** Possible outputs of the emotion analysis.**/
enum class Emotions(val value: Int, val color: Color) {
    /** An error occurred in the execution of the program  **/
    ERROR(-1, Color.Transparent),

    /** The analyzed text doesn't have a predominant emotion  **/
    NON_DOMINANT(0, Color.Gray),

    /** The emotion in the analyzed text is predominantly joy **/
    JOY(1, Color.Yellow),

    /** The emotion in the analyzed text is predominantly sadness **/
    SADNESS(2, Color.Blue),

    /** The emotion in the analyzed text is predominantly anger **/
    ANGER(3, Color.Red),

    /** The emotion in the analyzed text is predominantly disgust **/
    DISGUST(4, Color.Green),

    /** The emotion in the analyzed text is predominantly fear **/
    FEAR(5, Color.Magenta),

    /** The emotion in the analyzed text is predominantly surprise **/
    SURPRISE(6, Color.White),

    /** The text hasn't been analyzed **/
    NONE(7, Color.Transparent),
}