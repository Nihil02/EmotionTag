package com.nihil.emotiontag.data

/** Data about the apps screens.**/
enum class ScreenData(val route: String) {
    EntriesScreenData("entries"),
    AddEntryScreenData("addEntry"),
    UpdateEntryScreenData("updateEntry"),
    ShowEntryScreenData("showEntry"),
    StatisticsScreenData("statistics"),
}