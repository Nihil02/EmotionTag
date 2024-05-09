package com.nihil.emotiontag.data

import java.time.LocalDate

data class EntryData(
    val title: String = "",
    val text: String = "",
    val emotion: String = "",
    val date: LocalDate = LocalDate.of(2024, 1, 1),
)