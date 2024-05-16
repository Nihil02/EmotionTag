package com.nihil.emotiontag.data

/** Possible outputs of the emotion analysis.**/
enum class Emotions(val value: Int) {
    /** An error occurred in the execution of the program  **/
    ERROR(-1),

    /** The analyzed text doesn't have a predominant emotion  **/
    NON_DOMINANT(0),

    /** The emotion in the analyzed text is predominantly joy **/
    JOY(1),

    /** The emotion in the analyzed text is predominantly sadness **/
    SADNESS(2),

    /** The emotion in the analyzed text is predominantly anger **/
    ANGER(3),

    /** The emotion in the analyzed text is predominantly disgust **/
    DISGUST(4),

    /** The emotion in the analyzed text is predominantly fear **/
    FEAR(5),

    /** The emotion in the analyzed text is predominantly surprise **/
    SURPRISE(6),

    /** The text hasn't been analyzed **/
    NONE(7),
}